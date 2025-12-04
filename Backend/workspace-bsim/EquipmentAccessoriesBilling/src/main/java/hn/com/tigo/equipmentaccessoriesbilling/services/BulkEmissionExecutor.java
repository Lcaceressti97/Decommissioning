package hn.com.tigo.equipmentaccessoriesbilling.services;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import hn.com.tigo.equipmentaccessoriesbilling.entities.*;
import hn.com.tigo.equipmentaccessoriesbilling.exceptions.BadRequestException;
import hn.com.tigo.equipmentaccessoriesbilling.exceptions.EmissionStepException;
import hn.com.tigo.equipmentaccessoriesbilling.models.*;
import hn.com.tigo.equipmentaccessoriesbilling.provider.ProcessQueue;
import hn.com.tigo.equipmentaccessoriesbilling.provider.VoucherProviderBulkEmission;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.*;
import hn.com.tigo.equipmentaccessoriesbilling.services.executeorderservice.OrderResponse;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.*;
import hn.com.tigo.equipmentaccessoriesbilling.services.payupfrontservice.AdapterException_Exception;
import hn.com.tigo.equipmentaccessoriesbilling.services.payupfrontservice.TaskResponseType;
import hn.com.tigo.equipmentaccessoriesbilling.utils.Constants;
import hn.com.tigo.equipmentaccessoriesbilling.utils.ReadFilesConfig;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.BiConsumer;


@Service
@RequiredArgsConstructor
public class BulkEmissionExecutor implements IBulkEmissionExecutor {

    private final IBillingRepository billingRepository;
    private final IChannelRepository channelRepository;
    private final IPayUpFrontService payUpFrontService;
    private final VoucherProviderBulkEmission voucherProvider;
    private final INonFiscalNoteService nonFiscalNoteService;
    private final AuthenticationBsimService authenticationService;
    private final UnloadStockBsimService unloadStockBsimService;
    private final ProcessQueue processQueue;
    private final IProvisioningInsuranceService provisioningInsuranceService;
    private final IInsuranceClaimRepository insuranceClaimRepository;
    private final IEquipmentInsuranceControlRepository equipmentInsuranceControlRepository;
    private final ILogsService logsService;
    private final IControlAuthEmissionRepository controlAuthEmissionRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public BulkEmissionItemResult emitOneIsolated(Long idPrefecture, BulkEmissionContext ctx) {
        long start = System.currentTimeMillis();

        BulkEmissionItemResult.BulkEmissionItemResultBuilder out =
                BulkEmissionItemResult.builder().idPrefecture(idPrefecture).success(false);

        try {
            // === Carga y validaciones iniciales ===
            BillingEntity billing = billingRepository.findById(idPrefecture).orElse(null);
            if (billing == null) {
                throw new BadRequestException(String.format(Constants.ERROR_INVOICE_NOT_EXISTS, idPrefecture));
            }

            // evita re-emisión
            if (billing.getStatus() != null && billing.getStatus() != 0 && billing.getStatus() != 1) {
                logsService.saveLog(
                        8,
                        idPrefecture,
                        "Emisión masiva: factura ya estaba emitida, se omite reproceso",
                        ctx.getUserUpper(),
                        System.currentTimeMillis() - start
                );

                return out
                        .success(true)
                        .message("Factura ya había sido emitida previamente. Se omitió reproceso.")
                        .invoiceNo(billing.getInvoiceNo())
                        .build();
            }

            Params1002 p1002 = (Params1002) ctx.getParams1002();
            userPermissionValidation(ctx, p1002);
            validateBillingForIssue(billing, p1002);

            ChannelEntity channel = channelRepository.findById(billing.getChannel()).orElse(null);
            if (channel == null) {
                throw new BadRequestException(Constants.ERROR_NOT_FOUND_RECORD_CHANNEL);
            }

            // === Flujo de emisión por pasos ===
            VoucherAppliedData voucherData = callStep("VOUCHER",
                    () -> executionAddVoucher(billing, idPrefecture, ctx, p1002, channel));

            applyVoucherDataToBilling(billing, voucherData, false);
            billingRepository.save(billing);

            PayUpfrontResult puf = callStep("PAY_UP_FRONT",
                    () -> executionPayUpFrontService(channel, billing, p1002));

            runStep("NON_FISCAL_NOTE",
                    () -> executionNonFiscalNoteService(channel, billing, idPrefecture, ctx, p1002));

            //runStep("UNLOAD_STOCK",
            //      () -> executionUnloadReservedStockService(channel, billing, idPrefecture, ctx));

            runStep("PROVISIONING",
                    () -> generationInsuranceSchemes(billing, idPrefecture));

            runStep("TRAMA",
                    () -> generationTrama(channel, billing, idPrefecture, ctx, puf));

            // === Persistencias finales ===
            applyVoucherDataToBilling(billing, voucherData, true);
            String payCode = ctx.getPaymentCode();
            if (payCode != null && !payCode.trim().isEmpty()) {
                billing.setPaymentCode(payCode.trim());
            }
            billingRepository.save(billing);


            ControlAuthEmissionEntity entity = new ControlAuthEmissionEntity();
            entity.setIdPrefecture(idPrefecture);
            entity.setTypeApproval(((Params1002) ctx.getParams1002()).getTypeApprovalIssue());
            entity.setDescription(ctx.getDescription());
            entity.setPaymentCode(ctx.getPaymentCode());
            entity.setUserCreate(ctx.getUserUpper());
            entity.setCreated(LocalDateTime.now());
            controlAuthEmissionRepository.save(entity);

            logsService.saveLog(8, idPrefecture, "Emisión masiva: OK", ctx.getUserUpper(),
                    System.currentTimeMillis() - start);

            return out
                    .success(true)
                    .message("Emitida OK")
                    .invoiceNo(billing.getInvoiceNo())
                    .payUpfrontSerialNo(puf != null ? puf.serial : null)
                    .build();

        } catch (EmissionStepException ee) {
            // Errores en pasos (PAY_UP_FRONT, VOUCHER, etc) con metadatos específicos
            logsService.saveLog(7, idPrefecture, "Emisión masiva: " + ee.getMessage(), ctx.getUserUpper(),
                    System.currentTimeMillis() - start);

            return out
                    .message(ee.getUserMessage())
                    .service(ee.getService())
                    .errorCode(ee.getErrorCode())
                    .build();

        } catch (BadRequestException be) {
            // Validaciones de negocio previas (usuario, estado, tipo, canal, etc.)
            EmissionStepException wrapped = wrap("VALIDATION", be);
            logsService.saveLog(7, idPrefecture, "Emisión masiva: " + wrapped.getMessage(), ctx.getUserUpper(),
                    System.currentTimeMillis() - start);

            return out
                    .message(wrapped.getUserMessage())
                    .service(wrapped.getService())
                    .errorCode(wrapped.getErrorCode())
                    .build();

        } catch (Exception e) {
            // Cualquier otro fallo inesperado
            EmissionStepException wrapped = wrap("GENERAL", e);
            logsService.saveLog(7, idPrefecture, "Emisión masiva (unexpected): " + wrapped.getMessage(), ctx.getUserUpper(),
                    System.currentTimeMillis() - start);

            return out
                    .message(wrapped.getUserMessage())
                    .service(wrapped.getService())
                    .errorCode(wrapped.getErrorCode())
                    .build();
        }
    }

    /**
     * Prefija el servicio y normaliza el mensaje de error para trazabilidad en el batch
     */
    private EmissionStepException wrap(String service, Exception e) {
        String raw = extractMeaningfulMessage(e);
        String lower = raw.toLowerCase(Locale.ROOT);

        String code;
        if (e instanceof BadRequestException) code = "BAD_REQUEST";
        else if (lower.contains("connection refused")) code = "CONNECTION_REFUSED";
        else if (lower.contains("timeout") || lower.contains("timed out")) code = "TIMEOUT";
        else if (lower.contains("bad request") || lower.contains("badrequest") || lower.contains("400"))
            code = "BAD_REQUEST";
        else if (lower.contains("unauthorized") || lower.contains("401")) code = "UNAUTHORIZED";
        else if (lower.contains("forbidden") || lower.contains("403")) code = "FORBIDDEN";
        else if (lower.contains("not found") || lower.contains("404")) code = "NOT_FOUND";
        else code = "UNEXPECTED";

        return new EmissionStepException(service, code, raw, e);
    }


    // ================= Helpers =================

    /* Validaciones: Si el usuario tiene permisos para emitir facturas por tipo de usuario y control de permisos */
    private void userPermissionValidation(BulkEmissionContext ctx, Params1002 p1002) {
        List<String> typeUserEmit = p1002.typeUserEmit;
        if (typeUserEmit == null || !typeUserEmit.contains(ctx.getTypeUser().getTypeUser())) {
            throw new BadRequestException(Constants.ERROR_TYPE_USER_EMISSION);
        }
        if ("N".equalsIgnoreCase(ctx.getUserPerm().getGenerateBill())) {
            throw new BadRequestException(Constants.USER_NOT_PERMISSIONS_ISSUE_INVOICE);
        }
    }

    /*  Validaciones: Estado de factura, Tipo de factura, Autorización previa  */
    private void validateBillingForIssue(BillingEntity billing, Params1002 p1002) {
        if (billing.getStatus() != 0 && billing.getStatus() != 1) {
            throw new BadRequestException(String.format(Constants.ERROR_INVOICE_ISSUED, billing.getId()));
        }
        boolean typeOk = p1002.creditInvoiceTypes.contains(billing.getInvoiceType())
                || p1002.cashInvoiceTypes.contains(billing.getInvoiceType());
        if (!typeOk) throw new BadRequestException(Constants.ERROR_INVOICE_TYPE);

        if (billing.getStatus() == 0 && !p1002.cashInvoiceTypes.contains(billing.getInvoiceType())) {
            throw new BadRequestException(Constants.ERROR_INVOICE_NOT_AUTHORIZED);
        }
    }


    /* Ejecución servicio de PayUpFront */
    private PayUpfrontResult executionPayUpFrontService(ChannelEntity channel, BillingEntity billing, Params1002 p1002) throws AdapterException_Exception {
        if (channel.getPayUpFrontNumber() != 1) return null;

        delay(p1002.millisecondsPayUpfront);
        TaskResponseType task = payUpFrontService.executeTask(billing, channel);
        validateResponsePayUpFront(task);

        String json = task.getParameters().getParameter().get(0).getValue();
        JsonObject result = JsonParser.parseString(json).getAsJsonObject().getAsJsonObject("payUpfrontResult");

        String payUpfrontSerialNo = result.get("payUpfrontSerialNo").getAsString();
        String invoiceNo = result.get("invoiceNo").getAsString();

        billing.setInvoiceNo(invoiceNo.concat(" ").concat(payUpfrontSerialNo));
        return new PayUpfrontResult(payUpfrontSerialNo, invoiceNo);
    }

    /* Ejecución servicio de add voucher */
    private VoucherAppliedData executionAddVoucher(
            BillingEntity billing,
            Long idPrefecture,
            BulkEmissionContext ctx,
            Params1002 p1002,
            ChannelEntity channel
    ) throws Exception {

        Long branchId = (ctx.getIdBranchOffices() != null && ctx.getIdBranchOffices() != 0)
                ? ctx.getIdBranchOffices()
                : billing.getIdBranchOffices();

        return voucherProvider.executeAddVoucher(
                idPrefecture,
                ctx.getUserUpper(),
                branchId,
                p1002.getTypeApprovalIssue(),
                ctx.getParams1001(),
                channel
        );
    }


    private void applyVoucherDataToBilling(BillingEntity billing, VoucherAppliedData v, boolean applyStatus) {
        if (v == null) return;
        billing.setIdCompany(v.idCompany);
        billing.setAgency(v.agency);
        billing.setIdBranchOffices(v.idBranchOffices);
        billing.setInitialRank(v.initialRank);
        billing.setFinalRank(v.finalRank);
        billing.setIdSystem(v.idSystem);
        billing.setNumberDei(v.numberDei);
        billing.setDeadLine(v.deadLine);
        billing.setIdReference(v.idReference);
        billing.setCai(v.cai);
        billing.setIdVoucher(v.idVoucher);
        billing.setUserIssued(v.userIssued);
        billing.setDateOfIssue(v.dateOfIssue);
        billing.setXml(v.xml);
        billing.setTotalLps(v.totalLps);
        billing.setTotalLpsLetters(v.totalLpsLetters);
        if (applyStatus) {
            billing.setStatus(v.status);
        }
    }


    /* Ejecución servicio de NonFiscalNote */
    private void executionNonFiscalNoteService(ChannelEntity channel, BillingEntity billing, Long idPrefecture,
                                               BulkEmissionContext ctx, Params1002 p1002) {
        boolean isCash = p1002.cashInvoiceTypes.contains(billing.getInvoiceType());
        if (channel.getNonFiscalNote() != 1 || !isCash) return;

        Long branchId = (ctx.getIdBranchOffices() != null && ctx.getIdBranchOffices() != 0)
                ? ctx.getIdBranchOffices() : billing.getIdBranchOffices();

        ControlAuthEmissionModel m = buildControlModel(idPrefecture, ctx.getUserUpper(),
                ctx.getDescription(), branchId, p1002.typeApprovalIssue);

        OrderResponse r = nonFiscalNoteService.executeTask(billing, m, channel);

        if (r.getCode() != 0) throw new BadRequestException("Error Non Fiscal Note: " + r.getMessage());
    }

    /* Ejecución servicio de unloadReservedStock */
    private void executionUnloadReservedStockService(ChannelEntity channel, BillingEntity billing,
                                                     Long idPrefecture, BulkEmissionContext ctx) {
        if (channel.getInventoryDownload() != 1) return;

        AuthenticationBsimResponse tokenResp = authenticationService.getAccessToken();
        String token = tokenResp.getAccess_token();

        for (InvoiceDetailEntity d : billing.getInvoiceDetails()) {
            if (StringUtils.isBlank(d.getSerieOrBoxNumber())) continue;

            unloadStockBsimService.unloadReservedStock(
                    token,
                    billing.getInventoryType(),
                    d.getModel(),
                    billing.getWarehouse(),
                    billing.getSubWarehouse(),
                    d.getReserveKey(),
                    Collections.singletonList(d),
                    idPrefecture,
                    ctx.getUserUpper(),
                    channel
            );
        }
    }

    /* Generación trama de seguros */
    private void generationInsuranceSchemes(BillingEntity billing, Long idPrefecture) throws IOException {
        if (!"Facturación por Reclamo de Seguros".equals(billing.getFiscalProcess())) return;

        InsuranceClaimEntity claim = insuranceClaimRepository.getInsuranceClaimByInvoiceNumber(idPrefecture);
        OrderResponse r = provisioningInsuranceService.executeTask(claim, false);
        if (r.getCode() != 0) throw new BadRequestException("Error Provisioning Insurance: " + r.getMessage());

        createBothInsuranceControls(claim);
    }

    private void generationTrama(ChannelEntity channel, BillingEntity billing, Long idPrefecture,
                                 BulkEmissionContext ctx, PayUpfrontResult puf) throws IOException {
        if (channel.getGenerateTrama() != 1) return;

        processQueue.getProps();
        ReadFilesConfig readCfg = new ReadFilesConfig();

        Long branchId = (ctx.getIdBranchOffices() != null && ctx.getIdBranchOffices() != 0)
                ? ctx.getIdBranchOffices() : billing.getIdBranchOffices();

        ControlAuthEmissionModel m = buildControlModel(idPrefecture, ctx.getUserUpper(),
                ctx.getDescription(), branchId, ((Params1002) ctx.getParams1002()).getTypeApprovalIssue());

        boolean requirePUF = channel.getPayUpFrontNumber() == 1;
        validateTramaFields(billing, m, requirePUF, (puf != null ? puf.serial : null), (puf != null ? puf.invoice : null));

        String trama = generateTrama(m, billing, puf != null ? puf.serial : null, puf != null ? puf.invoice : null);
        processQueue.processTrama(readCfg, 0L, trama, channel);
        billing.setTramaEmission(trama);
    }


    // ====================== utils ======================

    private static class PayUpfrontResult {
        final String serial;
        final String invoice;

        PayUpfrontResult(String serial, String invoice) {
            this.serial = serial;
            this.invoice = invoice;
        }
    }

    private void delay(long ms) {
        if (ms <= 0) return;
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new BadRequestException("The thread was interrupted");
        }
    }

    private void validateResponsePayUpFront(TaskResponseType task) {
        if (task == null || task.getParameters() == null
                || task.getParameters().getParameter().isEmpty()
                || task.getParameters().getParameter().get(0).getValue() == null) {
            throw new BadRequestException("Error Pay Up Front: respuesta nula o inválida del proveedor");
        }

        String json = task.getParameters().getParameter().get(0).getValue();
        JsonObject root = JsonParser.parseString(json).getAsJsonObject();
        JsonObject header = root.getAsJsonObject("resultHeader");
        if (header == null) {
            throw new BadRequestException("Error Pay Up Front: falta resultHeader");
        }

        String code = header.get("resultCode").getAsString();
        String desc = header.has("resultDesc") && !header.get("resultDesc").isJsonNull()
                ? header.get("resultDesc").getAsString()
                : "sin descripción";

        if (!"0".equals(code)) {
            throw new BadRequestException("(code=" + code + "): " + desc);
        }
    }


    private String extractMeaningfulMessage(Throwable e) {
        Throwable root = e;
        while (root.getCause() != null) root = root.getCause();

        if (root.getMessage() != null && !root.getMessage().isEmpty()) return root.getMessage();
        if (e.getMessage() != null && !e.getMessage().isEmpty()) return e.getMessage();

        StackTraceElement ste = (root.getStackTrace() != null && root.getStackTrace().length > 0)
                ? root.getStackTrace()[0]
                : (e.getStackTrace() != null && e.getStackTrace().length > 0 ? e.getStackTrace()[0] : null);

        String location = (ste != null)
                ? (ste.getClassName() + "." + ste.getMethodName() + "(" + ste.getFileName() + ":" + ste.getLineNumber() + ")")
                : "<no-stack>";
        return root.getClass().getName() + " at " + location;
    }


    private ControlAuthEmissionModel buildControlModel(Long idPrefecture, String userUpper,
                                                       String description, Long branchOfficesId,
                                                       long typeApprovalIssue) {
        ControlAuthEmissionModel m = new ControlAuthEmissionModel();
        m.setIdPrefecture(idPrefecture);
        m.setUserCreate(userUpper);
        m.setDescription(description != null ? description : "EMISION MASIVA");
        m.setIdBranchOffices(branchOfficesId);
        m.setTypeApproval(typeApprovalIssue);
        return m;
    }

    public String generateTrama(ControlAuthEmissionModel controlAuthEmissionModel, BillingEntity billingEntity,
                                String payUpfrontSerialNo, String invoiceNo) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        StringBuilder marco = new StringBuilder("FACTURACION");
        marco.append("|EMISION");
        marco.append("|SUBSNUMB=")
                .append(billingEntity.getPrimaryIdentity() != null && !billingEntity.getPrimaryIdentity().isEmpty()
                        ? billingEntity.getPrimaryIdentity()
                        : billingEntity.getAcctCode());
        marco.append("|CUENTA_CLIENTE=").append(billingEntity.getCustomerId());
        marco.append("|CUENTA_FACTURACION=").append(billingEntity.getAcctCode());
        marco.append("|ID_PEDIDO_VENTA=").append(
                billingEntity.getUti() != null && !billingEntity.getUti().isEmpty() ? billingEntity.getUti() : 0);
        marco.append("|NUMERO_PREFACTURA=").append(billingEntity.getId());
        marco.append("|NUMERO_FACTURA=").append(billingEntity.getNumberDei());
        marco.append("|FECHAEMISION=").append(billingEntity.getDateOfIssue().format(formatter));
        marco.append("|IMEI=").append(billingEntity.getInvoiceDetails().get(0).getSerieOrBoxNumber());
        marco.append("|TOTAL_FACTURADO=").append(billingEntity.getAmountTotal());
        marco.append("|IMPUESTO=").append(billingEntity.getTaxPercentage());
        marco.append("|TIPO_FACTURA=").append(billingEntity.getInvoiceType());
        marco.append("|CAI=").append(billingEntity.getCai());
        marco.append("|USUARIO=").append(controlAuthEmissionModel.getUserCreate().toUpperCase());
        marco.append("|SERIAL_NUM=").append(payUpfrontSerialNo);
        marco.append("|INVOICE_NO=").append(invoiceNo);
        marco.append("|");

        return marco.toString();
    }

    private void validateTramaFields(BillingEntity b,
                                     ControlAuthEmissionModel c,
                                     boolean requirePayUpfront,
                                     String pufSerial,
                                     String invoiceNo) {

        List<String> errs = new ArrayList<>();

        // Helpers
        BiConsumer<String, Object> req = (name, val) -> {
            if (val == null) errs.add(name + " es nulo");
            else if (val instanceof String && org.apache.commons.lang3.StringUtils.isBlank((String) val))
                errs.add(name + " vacío");
        };

        // Datos base
        req.accept("DateOfIssue", b.getDateOfIssue());
        req.accept("CustomerId", b.getCustomerId());
        req.accept("AcctCode", b.getAcctCode());
        req.accept("InvoiceType", b.getInvoiceType());
        req.accept("NumberDei", b.getNumberDei());
        req.accept("CAI", b.getCai());
        req.accept("AmountTotal", b.getAmountTotal());
        req.accept("TaxPercentage", b.getTaxPercentage());
        req.accept("Usuario", c != null ? c.getUserCreate() : null);

        if (org.apache.commons.lang3.StringUtils.isBlank(b.getPrimaryIdentity())
                && org.apache.commons.lang3.StringUtils.isBlank(b.getAcctCode())) {
            errs.add("SUBSNUMB (PrimaryIdentity o AcctCode) vacío");
        }

        // Detalle + IMEI
        if (b.getInvoiceDetails() == null || b.getInvoiceDetails().isEmpty()) {
            errs.add("InvoiceDetails vacío");
        } else {
            List<InvoiceDetailEntity> details = b.getInvoiceDetails();
            String imei = "";
            for (int i = 0; i < details.size(); i++) {
                InvoiceDetailEntity detail = details.get(i);
                if (!detail.getModel().equals("200") &&
                        !detail.getDescription().toLowerCase().contains("simcard") &&
                        !detail.getDescription().toLowerCase().contains("sim card")) {
                    imei = detail.getSerieOrBoxNumber();
                }
            }
            //String imei = b.getInvoiceDetails().get(0).getSerieOrBoxNumber();
            if (org.apache.commons.lang3.StringUtils.isBlank(imei)) {
                errs.add("IMEI/Serie del primer detalle vacío");
            }
        }

        // PayUpFront requerido (si el canal lo exige)
        if (requirePayUpfront) {
            req.accept("PayUpfrontSerialNo", pufSerial);
            req.accept("InvoiceNo (PUF)", invoiceNo);
        }

        if (!errs.isEmpty()) {
            throw new BadRequestException("TRAMA: campos inválidos -> " + String.join(", ", errs));
        }
    }

    public void createBothInsuranceControls(InsuranceClaimEntity insuranceClaimEntity) throws IOException {
        createInsuranceControls(
                insuranceClaimEntity.getActualEsn(),
                insuranceClaimEntity.getNewEsn(),
                "SEG61",
                "SEG62"
        );
    }

    private void createInsuranceControls(String src, String dst, String t1, String t2) throws IOException {
        List<EquipmentInsuranceControlEntity> list =
                equipmentInsuranceControlRepository.getEquipmentInsuranceControlByEsn(src);
        if (list.isEmpty()) return;
        EquipmentInsuranceControlEntity base = list.get(0);
        createInsuranceControl(base, dst, t1);
        createInsuranceControl(base, src, t2);
    }

    private void createInsuranceControl(EquipmentInsuranceControlEntity base, String series, String tx) throws IOException {
        EquipmentInsuranceControlEntity e = new EquipmentInsuranceControlEntity();
        e.setTransactionCode(tx);
        e.setUserAs(base.getUserAs());
        e.setDateConsultation(base.getDateConsultation());
        e.setCustomerAccount(base.getCustomerAccount());
        e.setServiceAccount(base.getServiceAccount());
        e.setBillingAccount(base.getBillingAccount());
        e.setPhoneNumber(base.getPhoneNumber());
        e.setEquipmentModel(base.getEquipmentModel());
        e.setEsn(series);
        e.setOriginAs(base.getOriginAs());
        e.setInventoryTypeAs(base.getInventoryTypeAs());
        e.setOriginTypeAs(base.getOriginTypeAs());
        e.setDateContract(base.getDateContract());
        e.setInsuranceRate(base.getInsuranceRate());
        e.setPeriod(base.getPeriod());
        e.setInsuranceRate2(base.getInsuranceRate2());
        e.setPeriod2(base.getPeriod2());
        e.setInsuranceRate3(base.getInsuranceRate3());
        e.setPeriod3(base.getPeriod3());
        e.setInsuranceStatus(1L);
        e.setSubscriber(base.getSubscriber());

        if ("SEG61".equals(tx)) {
            processQueue.getProps();
            ReadFilesConfig readConfig = new ReadFilesConfig();
            long startTimeQueue = 0L;

            String trama = generateTramaInsurance(base, series);

            // En el servicio original usan channelId=3L
            ChannelEntity channelEntity = this.channelRepository.findById(3L).orElse(null);

            this.processQueue.processTrama(readConfig, startTimeQueue, trama, channelEntity);

            // Fechas: hoy a las 00:00 y +3 años
            java.time.LocalDate currentDate = java.time.LocalDate.now();
            java.time.LocalDateTime currentDateTime = currentDate.atStartOfDay();
            e.setDateInclusion(currentDateTime);
            e.setStartDate(currentDateTime);

            java.time.LocalDateTime endDateTime = currentDate.plusYears(3).atStartOfDay();
            e.setEndDate(endDateTime);

            e.setTrama(trama);
        } else {
            // Para otras transacciones (p.ej. SEG62) se copian las fechas/trama del base
            e.setDateInclusion(base.getDateInclusion());
            e.setStartDate(base.getStartDate());
            e.setEndDate(base.getEndDate());
            e.setTrama(base.getTrama());
        }

        equipmentInsuranceControlRepository.save(e);
    }

    public String generateTramaInsurance(EquipmentInsuranceControlEntity model, String newSeries) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        StringBuilder marco = new StringBuilder("SEGUROS");

        marco.append("|SERIE=").append(newSeries);
        marco.append("|MODELO=").append(model.getEquipmentModel());
        marco.append("|SUSCRIPTOR=").append(model.getSubscriber());
        marco.append("|TELEFONO=").append(model.getPhoneNumber());
        marco.append("|FECINICIO=").append(model.getStartDate().format(formatter));
        marco.append("|FECFIN=").append(model.getEndDate().format(formatter));
        marco.append("|");

        return marco.toString();
    }

    /**
     * Parámetros operativos provenientes de la app 1002.
     * Se construye típicamente en BulkEmissionServiceImpl y se pasa vía BulkEmissionContext.
     */
    public static class Params1002 {
        @Setter
        private long typeApprovalIssue;
        @Setter
        private long millisecondsPayUpfront;
        private Set<String> creditInvoiceTypes = new HashSet<>();
        private Set<String> cashInvoiceTypes = new HashSet<>();
        private List<String> typeUserEmit = new ArrayList<>();

        public long getTypeApprovalIssue() {
            return typeApprovalIssue;
        }

        public long getMillisecondsPayUpfront() {
            return millisecondsPayUpfront;
        }

        public Set<String> getCreditInvoiceTypes() {
            return creditInvoiceTypes;
        }

        public void setCreditInvoiceTypes(Set<String> v) {
            this.creditInvoiceTypes = v != null ? v : new HashSet<>();
        }

        public Set<String> getCashInvoiceTypes() {
            return cashInvoiceTypes;
        }

        public void setCashInvoiceTypes(Set<String> v) {
            this.cashInvoiceTypes = v != null ? v : new HashSet<>();
        }

        public List<String> getTypeUserEmit() {
            return typeUserEmit;
        }

        public void setTypeUserEmit(List<String> v) {
            this.typeUserEmit = v != null ? v : new ArrayList<>();
        }
    }

    @FunctionalInterface
    private interface ThrowingRunnable {
        void run() throws Exception;
    }

    @FunctionalInterface
    private interface ThrowingSupplier<T> {
        T get() throws Exception;
    }

    private void runStep(String service, ThrowingRunnable step) {
        try {
            step.run();
        } catch (Exception e) {
            throw wrap(service, e);
        }
    }

    private <T> T callStep(String service, ThrowingSupplier<T> step) {
        try {
            return step.get();
        } catch (Exception e) {
            throw wrap(service, e);
        }
    }

}
