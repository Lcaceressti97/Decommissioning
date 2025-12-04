package hn.com.tigo.equipmentaccessoriesbilling.services;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import hn.com.tigo.equipmentaccessoriesbilling.entities.*;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.*;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import javax.ws.rs.BadRequestException;

import hn.com.tigo.equipmentaccessoriesbilling.models.AuthenticationBsimResponse;
import hn.com.tigo.equipmentaccessoriesbilling.models.ConfigParametersModel;
import hn.com.tigo.equipmentaccessoriesbilling.models.ControlAuthEmissionModel;
import hn.com.tigo.equipmentaccessoriesbilling.provider.ProcessQueue;
import hn.com.tigo.equipmentaccessoriesbilling.provider.VoucherProvider;
import hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.VoucherResponseType;
import hn.com.tigo.equipmentaccessoriesbilling.services.executeorderservice.OrderResponse;
import hn.com.tigo.equipmentaccessoriesbilling.services.payupfrontservice.TaskResponseType;
import hn.com.tigo.equipmentaccessoriesbilling.utils.Constants;
import hn.com.tigo.equipmentaccessoriesbilling.utils.ReadFilesConfig;

@Service
public class ControlAuthEmissionServiceImpl implements IControlAuthEmissionService {

    private final IControlAuthEmissionRepository controlAuthEmissionRepository;
    private final IBillingRepository billingRepository;
    private final IControlUserPermissionsRepository controlUserPermissionsRepository;
    private final ILogsService logsService;
    private final VoucherProvider voucherProvider;
    private final IConfigParametersService configParametersService;
    private final ITypeUserRepository typeUserRepository;
    private final IPayUpFrontService payUpFrontService;
    private final IChannelRepository channelRepository;
    private final AuthenticationBsimService authenticationService;
    private final ReleaseSerialBsimService releaseSerialBsimService;
    private final UnloadStockBsimService unloadStockBsimService;
    private final INonFiscalNoteService nonFiscalNoteService;
    private final ProcessQueue processQueue;
    private final IEquipmentInsuranceControlRepository equipmentInsuranceControlRepository;
    private final IProvisioningInsuranceService provisioningInsuranceService;
    private final IInsuranceClaimRepository insuranceClaimRepository;

    public ControlAuthEmissionServiceImpl(IControlAuthEmissionRepository controlAuthEmissionRepository,
                                          IBillingRepository billingRepository, IControlUserPermissionsRepository controlUserPermissionsRepository,
                                          ILogsService logsService, VoucherProvider voucherProvider, IConfigParametersService configParametersService,
                                          ITypeUserRepository typeUserRepository, IPayUpFrontService payUpFrontService,
                                          IChannelRepository channelRepository, AuthenticationBsimService authenticationService,
                                          ReleaseSerialBsimService releaseSerialBsimService, UnloadStockBsimService unloadStockBsimService,
                                          INonFiscalNoteService nonFiscalNoteService, ProcessQueue processQueue,
                                          IEquipmentInsuranceControlRepository equipmentInsuranceControlRepository, IProvisioningInsuranceService provisioningInsuranceService, IInsuranceClaimRepository insuranceClaimRepository) {
        super();
        this.controlAuthEmissionRepository = controlAuthEmissionRepository;
        this.billingRepository = billingRepository;
        this.controlUserPermissionsRepository = controlUserPermissionsRepository;
        this.logsService = logsService;
        this.voucherProvider = voucherProvider;
        this.configParametersService = configParametersService;
        this.typeUserRepository = typeUserRepository;
        this.payUpFrontService = payUpFrontService;
        this.channelRepository = channelRepository;
        this.authenticationService = authenticationService;
        this.releaseSerialBsimService = releaseSerialBsimService;
        this.unloadStockBsimService = unloadStockBsimService;
        this.nonFiscalNoteService = nonFiscalNoteService;
        this.processQueue = processQueue;
        this.equipmentInsuranceControlRepository = equipmentInsuranceControlRepository;
        this.provisioningInsuranceService = provisioningInsuranceService;
        this.insuranceClaimRepository = insuranceClaimRepository;
    }

    @Override
    public List<ControlAuthEmissionModel> getAll() {
        List<ControlAuthEmissionEntity> entities = this.controlAuthEmissionRepository
                .findAll(Sort.by(Sort.Direction.DESC, "created"));
        return entities.stream().map(ControlAuthEmissionEntity::entityToModel).collect(Collectors.toList());
    }

    @Override
    public ControlAuthEmissionModel getById(Long id) {
        ControlAuthEmissionEntity entity = this.controlAuthEmissionRepository.findById(id).orElse(null);
        if (entity == null)
            throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID, id));
        return entity.entityToModel();
    }

    @Override
    public List<ControlAuthEmissionModel> getByUserCreate(String userCreate) {
        List<ControlAuthEmissionEntity> entities = controlAuthEmissionRepository.findByUserCreate(userCreate.toUpperCase());

        if (entities.isEmpty()) {
            throw new BadRequestException(String.format(Constants.NOT_USER_RECORDS_FOUND, userCreate));
        }

        return entities.stream().map(ControlAuthEmissionEntity::entityToModel).collect(Collectors.toList());
    }

    @Override
    public Page<ControlAuthEmissionModel> getByTypeApproval(Pageable pageable, Long typeApproval) {
        Pageable descendingPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("id").descending());

        Page<ControlAuthEmissionEntity> entities = controlAuthEmissionRepository.findByTypeApproval(descendingPageable, typeApproval);

        if (entities.isEmpty()) {
            throw new BadRequestException(String.format(Constants.ERROR_TYPE_APPROVAL, typeApproval.toString()));
        }

        return entities.map(ControlAuthEmissionEntity::entityToModel);
    }

    @Override
    public VoucherResponseType add(ControlAuthEmissionModel model) throws Exception {
        long startTime = System.currentTimeMillis();
        String payUpfrontSerialNo = null;
        String invoiceNo = null;

        try {
            // Tipo de autorización en números
            int typeIssued = 0;
            int typeAuthorized = 0;

            VoucherResponseType voucherResponseType = null;
            BillingEntity billingEntity = this.billingRepository.findById(model.getIdPrefecture()).orElse(null);

            ControlUserPermissionsEntity controlUserPermissions = controlUserPermissionsRepository
                    .findByUserName(model.getUserCreate().toUpperCase());

            /**
             * Validación si existe la factura
             *
             */
            if (billingEntity == null) {
                throw new BadRequestException(
                        String.format(Constants.ERROR_INVOICE_NOT_EXISTS, model.getIdPrefecture().toString()));
            }

            ChannelEntity channelEntity = this.channelRepository.findById(billingEntity.getChannel()).orElse(null);

            // Validación de la anulación del usuario
            validateUserAuthEmission(billingEntity, model, model.getUserCreate().toUpperCase());

            /**
             * Validamos al mismo tiempo dos cosas: 1,- Si el usuario existe 2,- Si tiene
             * permisos para autorizar o emitir
             *
             */
            if (controlUserPermissions == null) {
                throw new BadRequestException(String.format(Constants.NOT_USER_RECORDS_FOUND, model.getUserCreate()));
            }

            /**
             * Procedimiento de cargar los parámetros
             *
             */
            List<ConfigParametersModel> listTInvoiceType = this.configParametersService.getByIdApplication(1002L);
            Map<String, List<String>> parametersInvoiceType = new HashMap<>();
            Map<String, String> paramsTypeAuthorize = new HashMap<>();

            for (ConfigParametersModel parameter : listTInvoiceType) {
                String paramName = parameter.getParameterName();
                String paramValue = parameter.getParameterValue();

                if (!parametersInvoiceType.containsKey(paramName)) {
                    parametersInvoiceType.put(paramName, new ArrayList<>());
                }

                parametersInvoiceType.get(paramName).add(paramValue);
                paramsTypeAuthorize.put(paramName, paramValue);
            }

            // Seteando los valores de los tipos de autorización
            typeIssued = Integer.parseInt(paramsTypeAuthorize.get("TYPE_APPROVAL_ISSUE"));
            typeAuthorized = Integer.parseInt(paramsTypeAuthorize.get("TYPE_APPROVAL_AUTHORIZATION"));
            long millisecondsParam = Long.parseLong(paramsTypeAuthorize.get("MILLISECONDS_PAYUPFRONT"));
            /**
             * Se valida si el estado de la factura es diferente de 0 ó 1 y si el tipo de
             * aprobación es igual a 2
             *
             */
            if (billingEntity.getStatus() != 0 && billingEntity.getStatus() != 1
                    && model.getTypeApproval() == typeIssued) {
                throw new BadRequestException(String.format(Constants.ERROR_INVOICE_ISSUED, model.getIdPrefecture()));
            } else if (controlUserPermissions.getGenerateBill().equals("N") && model.getTypeApproval() == typeIssued) {
                throw new BadRequestException(Constants.USER_NOT_PERMISSIONS_ISSUE_INVOICE);
            }

            // Validación si el usuario es el vendedor de la factura
            if (controlUserPermissions.getIssueSellerInvoice().equals("N") && model.getTypeApproval() == typeIssued) {
                if (!billingEntity.getSeller().equals(model.getUserCreate().toUpperCase())) {
                    throw new BadRequestException(Constants.USER_NOT_SELLER);
                }
            }

            /**
             * Procedimiento de cargar los tipos de factura para no estar duplicando el
             * código
             *
             */
            // Validación emisión de facturas
            List<String> creditInvoiceTypes = parametersInvoiceType.get("CREDIT_INVOICE_TYPE");
            List<String> cashInvoiceTypes = parametersInvoiceType.get("CASH_INVOICE_TYPE");
            List<String> typeApprovalIssue = parametersInvoiceType.get("TYPE_APPROVAL_ISSUE");

            // Validación del tipo de aprobación
            if (model.getTypeApproval() == typeAuthorized) {
                // Validación autorización de facturas
                if (billingEntity.getStatus() != 0) {
                    throw new BadRequestException(Constants.ERROR_INVOICE_AUTHORIZED);
                } else if (controlUserPermissions.getAuthorizeInvoice().equals("N")) {
                    throw new BadRequestException(Constants.USER_NOT_PERMISSION_AUTHORIZED_INVOICE);
                }

                // Validación autorización de facturas al contado
                List<String> typeApprovalAuthorizations = parametersInvoiceType.get("TYPE_APPROVAL_AUTHORIZATION");
                if (typeApprovalAuthorizations != null && !typeApprovalAuthorizations.isEmpty()) {
                    boolean isTypeApprovalAuthorized = false;
                    for (String typeApprovalAuthorization : typeApprovalAuthorizations) {
                        if (model.getTypeApproval() == Long.parseLong(typeApprovalAuthorization)) {
                            isTypeApprovalAuthorized = true;
                            break;
                        }
                    }

                    if (isTypeApprovalAuthorized) {

                        if (cashInvoiceTypes != null && !cashInvoiceTypes.isEmpty()) {
                            if (cashInvoiceTypes.contains(billingEntity.getInvoiceType())) {
                                throw new BadRequestException(Constants.ERROR_CASH_INVOICE_AUTHORIZED);
                            }
                        }

                        if (creditInvoiceTypes != null && !creditInvoiceTypes.isEmpty()) {
                            if (creditInvoiceTypes.contains(billingEntity.getInvoiceType())) {
                                // Tipo de factura válido
                            } else {
                                throw new BadRequestException(Constants.ERROR_INVOICE_TYPE);
                            }
                        }
                    } else {
                        throw new BadRequestException(Constants.ERROR_INVALID_TYPE_APPROVAL);
                    }
                } else {
                    throw new BadRequestException(Constants.ERROR_CONFIGURATION_TYPE_APPROVAL);
                }
            } else if (model.getTypeApproval() == typeIssued) {

                /**
                 * Validamos que los parámetros se hayan cargado en los List
                 *
                 */
                if (creditInvoiceTypes != null && !creditInvoiceTypes.isEmpty() && typeApprovalIssue != null
                        && !typeApprovalIssue.isEmpty()) {

                    /**
                     * Validación sobre si existe el tipo de factura en los parámetros de tipo de
                     * factura al crédito
                     *
                     */
                    if (creditInvoiceTypes.contains(billingEntity.getInvoiceType())
                            || cashInvoiceTypes.contains(billingEntity.getInvoiceType())) {
                        boolean isTypeApprovalIssueAuthorized = false;

                        for (String typeApprovalAuth : typeApprovalIssue) {
                            if (model.getTypeApproval() == Long.parseLong(typeApprovalAuth)) {
                                isTypeApprovalIssueAuthorized = true;
                                break;
                            }
                        }

                        /**
                         * Validación si el tipo de transacción existe en los parámetros
                         *
                         */
                        if (isTypeApprovalIssueAuthorized) {

                            if (billingEntity.getStatus() == 0) {

                                /**
                                 * Validamos si es una factura al contado, si no es una factua al contado
                                 * entonces se manda la excepción
                                 *
                                 */
                                if (cashInvoiceTypes.contains(billingEntity.getInvoiceType())) {

                                } else {
                                    throw new BadRequestException(Constants.ERROR_INVOICE_NOT_AUTHORIZED);
                                }

                            }

                        } else {
                            throw new BadRequestException(Constants.ERROR_INVALID_TYPE_APPROVAL);
                        }

                    } else {
                        throw new BadRequestException(Constants.ERROR_INVOICE_TYPE);
                    }
                } else {
                    throw new BadRequestException(Constants.ERROR_CONFIGURATION_TYPE_ISSUED);
                }
            } else {
                throw new BadRequestException(Constants.ERROR_INVALID_TYPE_APPROVAL);
            }

            /**
             * Validación según el tipo de transacción 1 = Autorizar 2 = Emitir
             *
             */
            if (model.getTypeApproval() == typeIssued) {

                List<ConfigParametersModel> list = this.configParametersService.getByIdApplication(1001L);
                Map<String, String> parameters = new HashMap<>();
                for (ConfigParametersModel parameter : list) {
                    parameters.put(parameter.getParameterName(), parameter.getParameterValue());
                }

                Long branchOfficesId = (model.getIdBranchOffices() != null && model.getIdBranchOffices() != 0)
                        ? model.getIdBranchOffices()
                        : billingEntity.getIdBranchOffices();



                // Ejecucion del servicio PayUpFront
                if (channelEntity.getPayUpFrontNumber() == 1) {
                    delayExecution(millisecondsParam);

                    TaskResponseType taskResponseType = payUpFrontService.executeTask(billingEntity,channelEntity);

                    validateResponsePayUpFront(taskResponseType);

                    // Extraer información de Pay Up Front
                    String jsonResponse = taskResponseType.getParameters().getParameter().get(0).getValue();
                    JsonElement jsonElement = JsonParser.parseString(jsonResponse);
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    JsonObject payUpfrontResult = jsonObject.getAsJsonObject("payUpfrontResult");
                    payUpfrontSerialNo = payUpfrontResult.get("payUpfrontSerialNo").getAsString();
                    invoiceNo = payUpfrontResult.get("invoiceNo").getAsString();
                    billingEntity.setInvoiceNo(invoiceNo.concat(" ").concat(payUpfrontSerialNo));
                }

                // Ejecucion del servicio addVoucher
                voucherResponseType = voucherProvider.executeAddVoucher(model.getIdPrefecture(),
                        model.getUserCreate().toUpperCase(), branchOfficesId, model.getTypeApproval(), parameters,channelEntity);

                // Ejecucion del servicio NonFiscalNote
                if (channelEntity.getNonFiscalNote() == 1) {
                    if (cashInvoiceTypes.contains(billingEntity.getInvoiceType())) {
                        OrderResponse orderResponse = nonFiscalNoteService.executeTask(billingEntity, model,channelEntity);
                        if (orderResponse.getCode() != 0) {
                            throw new BadRequestException("Error Non Fiscal Note: " + orderResponse.getMessage());

                        }
                    }
                }

                // Liberacion de Serie
                if (channelEntity.getReleaseSerialNumber() == 1) {
                    for (InvoiceDetailEntity detail : billingEntity.getInvoiceDetails()) {
                        if (detail.getSerieOrBoxNumber() != null && !detail.getSerieOrBoxNumber().isEmpty()) {
                            AuthenticationBsimResponse accessToken = authenticationService.getAccessToken();
                            List<InvoiceDetailEntity> detailList = Arrays.asList(detail);

                            releaseSerialBsimService.releaseSeries(accessToken.getAccess_token(),
                                    billingEntity.getInventoryType(), detail.getModel(), billingEntity.getWarehouse(),
                                    billingEntity.getSubWarehouse(), detailList, model.getIdPrefecture(), model.getUserCreate(),channelEntity);

                        }
                    }
                }

                // Descarga de inventario
                if (channelEntity.getInventoryDownload() == 1) {
                    for (InvoiceDetailEntity detail : billingEntity.getInvoiceDetails()) {
                        if (detail.getSerieOrBoxNumber() != null && !detail.getSerieOrBoxNumber().isEmpty()) {
                            AuthenticationBsimResponse accessToken = authenticationService.getAccessToken();
                            List<InvoiceDetailEntity> detailList = Arrays.asList(detail);

                            unloadStockBsimService.unloadStock(accessToken.getAccess_token(),
                                    billingEntity.getInventoryType(), detail.getModel(), billingEntity.getWarehouse(),
                                    billingEntity.getSubWarehouse(), detailList, model.getIdPrefecture(), model.getUserCreate(),channelEntity);
                        }
                    }
                }

                if ("Facturación por Reclamo de Seguros".equals(billingEntity.getFiscalProcess())) {

                    long startTimeQueue = 0;

                    try {
                        InsuranceClaimEntity insuranceClaimEntity = this.insuranceClaimRepository
                                .getInsuranceClaimByInvoiceNumber(model.getIdPrefecture());

                        OrderResponse orderResponse = provisioningInsuranceService.executeTask(insuranceClaimEntity, false);

                        if (orderResponse.getCode() != 0) {
                            throw new BadRequestException("Error Provisioning Insurance: " + orderResponse.getMessage());

                        }

                        createBothInsuranceControls(insuranceClaimEntity);

                    } catch (Exception error) {
                        logsService.saveLog(8, model.getIdPrefecture(),
                                "An error occurred when issuing the invoice for the insurance claim: " + error.getMessage(),
                                model.getUserCreate(), startTimeQueue);
                    }
                }

            } else if (model.getTypeApproval() == typeAuthorized) {
                billingEntity.setAuthorizingUser(model.getUserCreate().toUpperCase());
                billingEntity.setAuthorizationDate(LocalDateTime.now());
                billingEntity.setStatus(1L);
                this.billingRepository.save(billingEntity);
            } else {
                throw new BadRequestException(Constants.ERROR_INVOICE_INVALID_STATUS);
            }

            ControlAuthEmissionEntity entity = new ControlAuthEmissionEntity();
            entity.setId(-1L);
            entity.setIdPrefecture(model.getIdPrefecture());
            entity.setTypeApproval(model.getTypeApproval());
            entity.setDescription(model.getDescription());
            entity.setPaymentCode(model.getPaymentCode());
            entity.setUserCreate(model.getUserCreate().toUpperCase());
            entity.setCreated(LocalDateTime.now());
            this.controlAuthEmissionRepository.save(entity);

            billingEntity.setPaymentCode(model.getPaymentCode());

            // Generacion de trama
            if (channelEntity.getGenerateTrama() == 1) {
                this.processQueue.getProps();
                ReadFilesConfig readConfig = null;
                long startTimeQueue = 0;

                try {
                    readConfig = new ReadFilesConfig();
                    String trama = generateTrama(model, billingEntity, payUpfrontSerialNo, invoiceNo);

                    startTimeQueue = this.processQueue.processTrama(readConfig, startTimeQueue, trama,channelEntity);
                    billingEntity.setTramaEmission(trama);

                } catch (Exception error) {
                    logsService.saveLog(8, model.getIdPrefecture(),
                            "An error occurred while creating the override: " + error.getMessage(),
                            model.getUserCreate(), startTimeQueue);
                }

            }

            this.billingRepository.save(billingEntity);

            return voucherResponseType;

        } catch (BadRequestException e) {
            long duration = System.currentTimeMillis() - startTime;
            logsService.saveLog(7, model.getIdPrefecture(),
                    "Error occurred while creating the authorization or issue: " + e.getMessage(),
                    model.getUserCreate(), duration);
            throw e;

        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            logsService.saveLog(7, model.getIdPrefecture(),
                    "Error occurred while creating the authorization or issue: " + e.getMessage(),
                    model.getUserCreate(), duration);
            throw e;
        }
    }

    private void validateUserAuthEmission(BillingEntity billingEntity, ControlAuthEmissionModel model, String user)
            throws BadRequestException {

        ControlUserPermissionsEntity controlUserPermissionsEntity = controlUserPermissionsRepository
                .findByUserName(user);
        if (controlUserPermissionsEntity == null)
            throw new BadRequestException(String.format(Constants.ERROR_USER_NOT_EXISTS, user));

        TypeUserEntity typeUserEntity = this.typeUserRepository.findById(controlUserPermissionsEntity.getTypeUser())
                .orElse(null);
        if (typeUserEntity == null)
            throw new BadRequestException(String.format(Constants.ERROR_USER_TYPE));

        List<ConfigParametersModel> listTypeUser = this.configParametersService.getByIdApplication(1002L);
        Map<String, List<String>> parametersTypeUser = new HashMap<>();
        Map<String, String> paramsTypeUser = new HashMap<>();

        for (ConfigParametersModel parameter : listTypeUser) {
            String paramName = parameter.getParameterName();
            String paramValue = parameter.getParameterValue();

            if (!parametersTypeUser.containsKey(paramName)) {
                parametersTypeUser.put(paramName, new ArrayList<>());
            }

            parametersTypeUser.get(paramName).add(paramValue);
            paramsTypeUser.put(paramName, paramValue);
        }

        List<String> typeUserAutorization = parametersTypeUser.get("TYPE_USER_AUTHORIZATION");
        List<String> typeUserEmit = parametersTypeUser.get("TYPE_USER_EMIT");
        int typeApprovalIssue = Integer.parseInt(paramsTypeUser.get("TYPE_APPROVAL_ISSUE"));
        int typeApprovalAuthorizations = Integer.parseInt(paramsTypeUser.get("TYPE_APPROVAL_AUTHORIZATION"));

        if (!typeUserAutorization.contains(typeUserEntity.getTypeUser().toString())
                && model.getTypeApproval() == typeApprovalAuthorizations) {
            throw new BadRequestException(Constants.ERROR_TYPE_USER_AUTH);

        } else if (!typeUserEmit.contains(typeUserEntity.getTypeUser().toString())
                && model.getTypeApproval() == typeApprovalIssue) {
            throw new BadRequestException(Constants.ERROR_TYPE_USER_EMISSION);

        }
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

    public void createInsuranceControls(String seriesToConsult, String newSeries, String transactionCode1, String transactionCode2) throws IOException {
        try {
            List<EquipmentInsuranceControlEntity> entities = this.equipmentInsuranceControlRepository
                    .getEquipmentInsuranceControlByEsn(seriesToConsult);

            if (!entities.isEmpty()) {
                EquipmentInsuranceControlEntity existingEntity = entities.get(0);

                createInsuranceControl(existingEntity, newSeries, transactionCode1);

                createInsuranceControl(existingEntity, seriesToConsult, transactionCode2);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void createInsuranceControl(EquipmentInsuranceControlEntity existingEntity, String series, String transactionCode) throws IOException {
        try {
            EquipmentInsuranceControlEntity newEntity = createNewEntityFromExisting(existingEntity, series, transactionCode);
            this.equipmentInsuranceControlRepository.save(newEntity);
        } catch (Exception e) {
            throw e;
        }
    }

    private EquipmentInsuranceControlEntity createNewEntityFromExisting(
            EquipmentInsuranceControlEntity existingEntity,
            String series,
            String transactionCode) throws IOException {

        EquipmentInsuranceControlEntity newEntity = new EquipmentInsuranceControlEntity();
        newEntity.setTransactionCode(transactionCode);
        newEntity.setUserAs(existingEntity.getUserAs());
        newEntity.setDateConsultation(existingEntity.getDateConsultation());
        newEntity.setCustomerAccount(existingEntity.getCustomerAccount());
        newEntity.setServiceAccount(existingEntity.getServiceAccount());
        newEntity.setBillingAccount(existingEntity.getBillingAccount());
        newEntity.setPhoneNumber(existingEntity.getPhoneNumber());
        newEntity.setEquipmentModel(existingEntity.getEquipmentModel());
        newEntity.setEsn(series);
        newEntity.setOriginAs(existingEntity.getOriginAs());
        newEntity.setInventoryTypeAs(existingEntity.getInventoryTypeAs());
        newEntity.setOriginTypeAs(existingEntity.getOriginTypeAs());
        newEntity.setDateContract(existingEntity.getDateContract());
        newEntity.setInsuranceRate(existingEntity.getInsuranceRate());
        newEntity.setPeriod(existingEntity.getPeriod());
        newEntity.setInsuranceRate2(existingEntity.getInsuranceRate2());
        newEntity.setPeriod2(existingEntity.getPeriod2());
        newEntity.setInsuranceRate3(existingEntity.getInsuranceRate3());
        newEntity.setPeriod3(existingEntity.getPeriod3());
        newEntity.setInsuranceStatus(1L);
        newEntity.setSubscriber(existingEntity.getSubscriber());


        if ("SEG61".equals(transactionCode)) {
            this.processQueue.getProps();
            ReadFilesConfig readConfig = null;
            long startTimeQueue = 0;

            readConfig = new ReadFilesConfig();
            String trama = generateTramaInsurance(existingEntity, series);

            ChannelEntity channelEntity = this.channelRepository.findById(3L).orElse(null);

            this.processQueue.processTrama(readConfig, startTimeQueue, trama,channelEntity);

            LocalDate currentDate = LocalDate.now();
            LocalDateTime currentDateTime = currentDate.atStartOfDay();
            newEntity.setDateInclusion(currentDateTime);
            newEntity.setStartDate(currentDateTime);

            LocalDateTime endDateTime = currentDate.plusYears(3).atStartOfDay();
            newEntity.setEndDate(endDateTime);

            newEntity.setTrama(trama);
        } else {
            newEntity.setDateInclusion(existingEntity.getDateInclusion());
            newEntity.setStartDate(existingEntity.getStartDate());
            newEntity.setEndDate(existingEntity.getEndDate());
            newEntity.setTrama(existingEntity.getTrama());
        }

        return newEntity;
    }

    public void createBothInsuranceControls(InsuranceClaimEntity insuranceClaimEntity) throws IOException {
        createInsuranceControls(
                insuranceClaimEntity.getActualEsn(),
                insuranceClaimEntity.getNewEsn(),
                "SEG61",
                "SEG62"
        );
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

    @Override
    public void update(Long id, ControlAuthEmissionModel model) {
        long startTime = System.currentTimeMillis();

        try {
            ControlAuthEmissionEntity entity = this.controlAuthEmissionRepository.findById(id).orElse(null);
            if (entity == null)
                throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_UPDATE, id));

            entity.setIdPrefecture(model.getIdPrefecture());
            entity.setTypeApproval(model.getTypeApproval());
            entity.setDescription(model.getDescription());
            entity.setUserCreate(model.getUserCreate().toUpperCase());
            entity.setCreated(LocalDateTime.now());
            this.controlAuthEmissionRepository.save(entity);
        } catch (BadRequestException e) {
            long duration = System.currentTimeMillis() - startTime;

            logsService.saveLog(7, model.getIdPrefecture(),
                    "Error occurred while updating the authorization or issue: " + e.getMessage(),
                    model.getUserCreate(), duration);
            throw e;

        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;

            logsService.saveLog(7, model.getIdPrefecture(),
                    "Error occurred while updating the authorization or issue: " + e.getMessage(),
                    model.getUserCreate(), duration);
            throw e;
        }
    }

    @Override
    public void delete(Long id) {
        ControlAuthEmissionEntity entity = this.controlAuthEmissionRepository.findById(id).orElse(null);
        if (entity == null)
            throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_DELETE, id));

        this.controlAuthEmissionRepository.delete(entity);
    }

    public void validateResponsePayUpFront(TaskResponseType taskResponseType) throws BadRequestException {
        try {
            // Obtén el valor JSON de la respuesta
            String jsonResponse = taskResponseType.getParameters().getParameter().get(0).getValue();

            // Parsear el JSON utilizando Gson
            JsonElement jsonElement = JsonParser.parseString(jsonResponse);
            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                // Verifica si la respuesta contiene el campo "resultHeader"
                if (jsonObject.has("resultHeader")) {
                    JsonObject resultHeader = jsonObject.getAsJsonObject("resultHeader");
                    String resultCode = resultHeader.get("resultCode").getAsString();

                    // Verifica el resultCode para determinar éxito o error
                    if ("0".equals(resultCode)) {
                        // Caso de éxito, continuar con tu lógica normal


                    } else {
                        String resultDesc = resultHeader.get("resultDesc").getAsString();
                        throw new BadRequestException("Error Pay Up Front: " + resultDesc);
                    }
                } else {
                    throw new BadRequestException("Invalid response: Missing resultHeader");
                }

            } else {
                throw new BadRequestException("Invalid response format");
            }

        } catch (JsonSyntaxException e) {
            throw new BadRequestException("Invalid response format", e);
        }
    }

    private void delayExecution(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new BadRequestException("The thread was interrupted");
        }
    }
}