package hn.com.tigo.equipmentaccessoriesbilling.provider;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Map;

import javax.ws.rs.BadRequestException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import javax.xml.ws.WebServiceException;

import com.google.gson.Gson;
import hn.com.tigo.equipmentaccessoriesbilling.entities.ChannelEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.*;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.ILogsServicesService;
import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.AmountType;
import hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.AmountsType;
import hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.BasicVoucherPort;
import hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.BasicVoucherService;
import hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.CancelVoucherType;
import hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.FieldType;
import hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.FieldsType;
import hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.GetVoucherType;
import hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.ItemAmountType;
import hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.ItemType;
import hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.ItemsType;
import hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.MessageFaultMsg;
import hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.VoucherResponseType;
import hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.VoucherSYSType;
import hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.VoucherType;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IBillingService;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IBranchOfficesService;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IInvoiceDetailService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.Constants;
import hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.TaxPayerType;
import hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.VoucherACKType;
import hn.com.tigo.equipmentaccessoriesbilling.services.basicvoucherservice.ItemAmountsType;

@Service
public class VoucherProvider {

    // Props
    private final IBillingService billingService;
    private final IInvoiceDetailService invoiceDetailService;
    private final IBranchOfficesService branchOfficesService;
    private final AddVoucherService addVoucherService;
    private final VoucherUtil voucherUtil;
    private final CancelVoucherService cancelVoucherService;
    private final ILogsServicesService logsServicesService;

    public VoucherProvider(IBillingService billingService, IInvoiceDetailService invoiceDetailService,
                           IBranchOfficesService branchOfficesService, AddVoucherService addVoucherService, CancelVoucherService cancelVoucherService, ILogsServicesService logsServicesService) {
        this.billingService = billingService;
        this.invoiceDetailService = invoiceDetailService;
        this.branchOfficesService = branchOfficesService;
        this.addVoucherService = addVoucherService;
        this.cancelVoucherService = cancelVoucherService;
        this.logsServicesService = logsServicesService;
        this.voucherUtil = new VoucherUtil();
    }

    // Correcto

    /**
     * Método en uso, esta es la forma correcta
     *
     * @param id
     * @param user
     * @param parameters
     * @return
     * @throws Exception
     */
    public VoucherResponseType executeAddVoucher(final Long id, final String user, final Long idBranches,
                                                 Long typeApproval, final Map<String, String> parameters, ChannelEntity channelEntity) throws Exception, BadRequestException {

        VoucherResponseType voucherResponseType = null;

        try {

            BillingModel billingModel = this.billingService.findById(id);

            /**
             * Validamos si existe el id secuencial de la tabla padre si no se muestra un
             * BadRequest
             *
             */
            if (billingModel != null) {

                String estado = billingModel.getStatus().toString();

                if (estado.equals("0") || estado.equals("1")) {
                    // Obtenemos los detalles de la factura
                    List<InvoiceDetailModel> products = this.invoiceDetailService.getDetailByIdInvoice(id);
                    BranchOfficesModel branchOfficesModel = this.branchOfficesService.findById(idBranches);

                    if (products.isEmpty()) {
                        throw new BadRequestException(Constants.ERROR_INVOICE_DETAIL);
                    }

                    if (branchOfficesModel == null) {
                        throw new BadRequestException(String.format(Constants.ERROR_INVOICE_IDS, idBranches));
                    }

                    // Se construye el request
                    VoucherType voucherType = this.buildVoucherType(billingModel, products, branchOfficesModel,
                            parameters, user);

                    String request = this.addVoucherService.getRequest(voucherType, parameters);

                    String response = this.voucherUtil.consumeAddVoucher(request, parameters);

                    System.out.println("REQUEST: " +  request);

                    // Buscamos el índice de inicio y fin de la cadena deseada en caso de ERROR
                    int startIndex = response.indexOf("<Errors>");
                    int endIndex = response.indexOf("</Errors>") + "</Errors>".length();

                    /**
                     * Validamos si es un ERROR or OK
                     *
                     */
                    if (startIndex != -1 && endIndex != -1) {
                        // Extraemos la subcadena deseada
                        String subString = response.substring(startIndex, endIndex);

                        VoucherErrorModel error = this.voucherUtil.xmlToVoucherErrorModel(subString);

                        if (error == null) {
                            throw new BadRequestException(Constants.ERROR_MAPPER_VOUCHER);
                        } else {
                            throw new BadRequestException("Code: " + error.getCode() + ", " + error.getDescription());
                        }

                    } else {

                        String responseUpdate = response.replace("<?xml version='1.0' encoding='UTF-8'?>", "");

                        String responseFinal = responseUpdate.replace("http://www.w3.org/2003/05/soap-envelope",
                                "http://schemas.xmlsoap.org/soap/envelope/");

                        voucherResponseType = this.voucherUtil.xmlToVoucherResponseType(responseFinal);

                        if (voucherResponseType == null) {
                            throw new BadRequestException(Constants.ERROR_MAPPER_VOUCHER);
                        }
                    }

                    billingModel.setIdCompany(voucherType.getIDCompany());
                    billingModel.setAgency(branchOfficesModel.getName());
                    billingModel.setIdBranchOffices(branchOfficesModel.getId());
                    billingModel.setInitialRank(voucherResponseType.getInternal().getRange().getDeinumberStart());
                    billingModel.setFinalRank(voucherResponseType.getInternal().getRange().getDeinumberStop());
                    billingModel.setIdSystem(voucherType.getIDSystem());
                    billingModel.setNumberDei(voucherResponseType.getInternal().getNumberDEI());
                    billingModel.setDeadLine(voucherResponseType.getInternal().getDeadLine().toString());
                    billingModel.setIdReference(voucherResponseType.getInternal().getIDReference());
                    billingModel.setCai(voucherResponseType.getInternal().getCAI());
                    billingModel.setIdVoucher(voucherResponseType.getInternal().getIDVoucher());
                    billingModel.setUserIssued(user);
                    billingModel.setDateOfIssue(LocalDateTime.now());
                    billingModel.setStatus(2L);
                    billingModel.setXml(request);
                    billingModel.setTotalLps(voucherResponseType.getInternal().getLiteralAmount().toString());
                    billingModel.setTotalLpsLetters(voucherResponseType.getInternal().getLiteral());


                    // Actualizamos los campos
                    this.billingService.update(id, billingModel);

                    // Guardar el log
                    if (channelEntity.getLogs() == 1) {
                        Gson gson = new Gson();
                        LogsServiceModel logModel = new LogsServiceModel();
                        logModel.setRequest(request);
                        logModel.setResponse(gson.toJson(voucherResponseType));
                        logModel.setReference(id);
                        logModel.setService("VOUCHER SERVICE");
                        logModel.setUserCreate(user);
                        logModel.setExecutionTime(System.currentTimeMillis());

                        logsServicesService.saveLog(logModel);
                    }


                    return voucherResponseType;

                } else {
                    throw new BadRequestException(" La factura ya ha sido emitida");
                }

            } else {
                throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID, id));

            }

        } catch (Exception ex) {

            throw new Exception(ex.getMessage());
        }

    }

    /**
     * Método encargado de consumer el getVoucherService No esta en uso
     *
     * @param id
     */
    public void addVoucherService(final Long id, final String user, final Map<String, String> parameters) {

        BillingModel billingModel = this.billingService.findById(id);

        /**
         * Validamos si existe el id secuencial de la tabla padre si no se muestra un
         * BadRequest
         *
         */
        if (billingModel != null) {

            // Obtenemos los detalles de la factura
            List<InvoiceDetailModel> products = this.invoiceDetailService.getDetailByIdInvoice(id);
            BranchOfficesModel branchOfficesModel = this.branchOfficesService
                    .findById(billingModel.getIdBranchOffices());

            // Validamos si hay productos en la factura
            if (products.isEmpty()) {
                throw new BadRequestException("Error no se encontraron productos de la factura, se necesita productos");
            }

            if (branchOfficesModel == null) {
                throw new BadRequestException("Error no se encontraron los datos IDCompany y IDSystem");
            }

            try {

                // Instanciamos la clase para establecer la conexión al server
                BasicVoucherService basicVoucherService = new BasicVoucherService();
                BasicVoucherPort basicVoucherPort = basicVoucherService.getBasicVoucherService();

                // Se construye el request
                VoucherType voucherType = this.buildVoucherType(billingModel, products, branchOfficesModel, parameters,
                        user);

                // Ejecutamos el servicio del addVoucher
                VoucherResponseType voucherResponseType = basicVoucherPort.addVoucher(voucherType);

                // Seteamos los nuevos valores para actualizar en la tabla padre.

                billingModel.setIdCompany(voucherType.getIDCompany());
                billingModel.setIdSystem(voucherType.getIDSystem());
                billingModel.setNumberDei(voucherResponseType.getInternal().getNumberDEI());
                billingModel.setDeadLine(voucherResponseType.getInternal().getDeadLine().toString());
                billingModel.setIdReference(voucherResponseType.getInternal().getIDReference());
                billingModel.setCai(voucherResponseType.getInternal().getCAI());
                billingModel.setIdVoucher(voucherResponseType.getInternal().getIDVoucher());

                // Estado emitida
                billingModel.setStatus(2L);

                // Actualizamos los campos
                this.billingService.updateDataForAddVoucher(id, billingModel);

            } catch (WebServiceException e) { // Este controla el error de conexión al server

                System.out.println(e.getMessage());
                throw new BadRequestException(e.getMessage());

            } catch (MessageFaultMsg ex) {// Este controla si el servicio addVoucher falla
                System.out.println(ex.getMessage());
                throw new BadRequestException("Error al consumir el servicio addVoucher de VoucherService");

            }

        } else {
            throw new BadRequestException(String.format("Error get,Record with id %s is not valid", id));

        }

    }

    /**
     * Este método construye el request para consumir el addVoucher()
     *
     * @return VoucherType
     */
    private VoucherType buildVoucherType(BillingModel billingModel, List<InvoiceDetailModel> products,
                                         BranchOfficesModel branchOfficesModel, Map<String, String> parameters, String user) {

        // Instanciamos el objeto de retorno
        VoucherType voucherType = new VoucherType();

        // Instanciamos la fecha actual
        LocalDateTime fechaHoraActual = LocalDateTime.now();

        // Seteamos los primeros valores
        voucherType.setIDCompany(branchOfficesModel.getIdCompany());
        voucherType.setIDSystem(branchOfficesModel.getIdSystem());
        voucherType.setIDReference(this.buildIdReference(String.valueOf(billingModel.getId()), fechaHoraActual,
                branchOfficesModel.getIdSystem()));
        voucherType.setPeriod(this.buildXMLGregorianCalendar(fechaHoraActual));

        // Setear los valores del <Voucher>
        voucherType.setVoucher(this.buildVoucher(fechaHoraActual, billingModel, branchOfficesModel));

        // Setear a etiqueta <TaxPayerType>
        TaxPayerType taxPayerType = new TaxPayerType();
        taxPayerType.setTaxPayerType(1L);
        taxPayerType.setIDType(1L);

        String documentId = billingModel.getAcctCode();
        taxPayerType.setDocumentID(documentId);
        taxPayerType.setName(billingModel.getCustomer());

        voucherType.setTaxPayer(taxPayerType);

        // Setear la etiqueta <ReferenceSystem>
        FieldsType fieldsType = new FieldsType();

        FieldType fieldType = new FieldType();
        fieldType.setIDField(47L);
        fieldType.setValue(String.valueOf(billingModel.getId()));
        fieldsType.getField().add(fieldType);

        voucherType.setReferenceSystem(fieldsType);

        // Seteando el elemento <Items>
        ItemsType itemsType = new ItemsType();

        // Bucle para agregar los <item> que son los campos de los detalles de la tabla
        // hija
        for (InvoiceDetailModel product : products) {

            // Seteamos los datos generales
            ItemType itemType = new ItemType();
            itemType.setItemCode(product.getModel());
            itemType.setDescription(product.getDescription());

            BigDecimal quantity = new BigDecimal(product.getQuantity().toString());
            itemType.setQuantity(quantity);

            // Seteamos los datos monetario
            ItemAmountsType itemAmountsType = new ItemAmountsType();

            ItemAmountType itemAmountType = new ItemAmountType();
            itemAmountType.setIDCurrency(1L);

            BigDecimal unitPrice = new BigDecimal(product.getUnitPrice().toString());
            itemAmountType.setUnitPrice(unitPrice);

            BigDecimal total = new BigDecimal(product.getAmountTotal().toString());
            itemAmountType.setTotal(total);
            itemAmountsType.getItemAmount().add(itemAmountType);

            itemType.setItemsAmounts(itemAmountsType);

            // Agregamos los <Item> a la etiqueta <Items>
            itemsType.getItem().add(itemType);

        }

        // Agregamos los items al request
        voucherType.setItems(itemsType);

        // Instanciamos el objeto haciendo referencia a la etiqueta <Amounts>
        AmountsType amountsType = new AmountsType();
        Long idAmount = 1L;

        Long idCurrency = Long.parseLong(parameters.get("ID_CURRENCY"));
        Long idCurrency8 = Long.parseLong(parameters.get("ID_CURRENCY_FOR_8"));

        // Realizar el calculo del subtotal-descuento
        BigDecimal roundedSubTotal = null;
        Double subtotal_discount = billingModel.getSubtotal();
        /**
         * Condicion para aplicar el descuento si lo tiene
         */
        if (billingModel.getDiscount() != null && billingModel.getDiscount() != 0.00) {

            subtotal_discount = billingModel.getSubtotal() - billingModel.getDiscount();

        }

        roundedSubTotal = BigDecimal.valueOf(subtotal_discount).setScale(4, RoundingMode.HALF_UP);

        // Bucle para agregar los 3 <Amount>
        for (int i = 0; i < 3; i++) {

            // Instanciamos el elemento de los amount
            AmountType amountType = new AmountType();
            amountType.setIDCurrency(idCurrency);
            amountType.setIDAmount(idAmount);

            BigDecimal amount = new BigDecimal(roundedSubTotal.toString());
            amountType.setAmount(amount);

            // Validamos que tipo de amount es para agregar en cero los valores del impuesto
            if (idAmount == 3L) {
                BigDecimal percentage = new BigDecimal(billingModel.getTaxPercentage().toString());
                BigDecimal tax = new BigDecimal(billingModel.getAmountTax().toString());
                amountType.setPercentage(percentage);
                amountType.setTax(tax);
            } else {
                BigDecimal percentage = BigDecimal.ZERO;
                BigDecimal tax = BigDecimal.ZERO;
                amountType.setPercentage(percentage);
                amountType.setTax(tax);
            }

            if (idAmount == 5L) {
                BigDecimal amountUpdate = new BigDecimal(billingModel.getAmountTotal().toString());
                amountType.setAmount(amountUpdate);
            }

            // Se agregan los <Amount> a la etiqueta <Amounts>
            amountsType.getAmount().add(amountType);
            idAmount = idAmount + 2L;
        }

        // Agregamos el amount de la tasa de cambio
        AmountType amountType = new AmountType();
        amountType.setIDCurrency(idCurrency8);
        amountType.setIDAmount(8L);

        BigDecimal percentage = BigDecimal.ZERO;
        BigDecimal tax = BigDecimal.ZERO;
        amountType.setPercentage(percentage);
        amountType.setTax(tax);

        BigDecimal amount = new BigDecimal(billingModel.getExchangeRate().toString());
        amountType.setAmount(amount);
        // Se agregan los <Amount> a la etiqueta <Amounts>
        amountsType.getAmount().add(amountType);

        voucherType.setAmounts(amountsType);

        voucherType.setUser(user);
        voucherType.setTerminal("1");

        return voucherType;

    }

    /**
     * Método que nos ayuda a construie el idReference
     *
     * @param id
     * @param fechaHoraActual
     * @return
     */
    private Long buildIdReference(String id, LocalDateTime fechaHoraActual, Long idSystem) {
        Long idReference = null;

        String year = String.valueOf(fechaHoraActual.getYear());

        String month = String.valueOf(fechaHoraActual.getMonthValue());
        if (month.length() == 1) {
            month = "0" + month;
        }

        String day = String.valueOf(fechaHoraActual.getDayOfMonth());
        if (day.length() == 1) {
            day = "0" + day;
        }

        String combinedValue = year + month + day + idSystem + id;
        idReference = Long.parseLong(combinedValue);

        return idReference;
    }

    /**
     * Método encargado de crear la fecha con formato yyyy-MM-dd pero del tipo de
     * dato XMLGregorianCalendar
     *
     * @param fechaHoraActual
     * @return
     */
    private XMLGregorianCalendar buildXMLGregorianCalendar(LocalDateTime fechaHoraActual) {

        try {
            DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
            // Convertir LocalDateTime a XMLGregorianCalendar
            // Obtener solo la fecha formateada
            String fechaFormateada = fechaHoraActual.format(DateTimeFormatter.ISO_LOCAL_DATE);

            // Convertir la fecha formateada a XMLGregorianCalendar
            XMLGregorianCalendar xmlGregorianCalendar = datatypeFactory.newXMLGregorianCalendar(fechaFormateada);
            return xmlGregorianCalendar;
        } catch (DatatypeConfigurationException e) {
            return null;
        }
    }

    /**
     * Este método nos ayuda a construir el elemento <Voucher>
     *
     * @param fechaHoraActual
     * @return
     */
    private VoucherType.Voucher buildVoucher(LocalDateTime fechaHoraActual, BillingModel billingModel,
                                             BranchOfficesModel brancheOffices) {

        // Instanciamos el objeto de retorno
        VoucherType.Voucher voucher = new VoucherType.Voucher();

        // Seteamos el valor de la etiqueta <Original>
        VoucherSYSType voucherSYSType = new VoucherSYSType();
        voucherSYSType.setVoucherDate(this.buildXMLGregorianCalendar(fechaHoraActual));

        BigInteger idPoint = BigInteger.valueOf(brancheOffices.getIdPoint());
        voucherSYSType.setIDPoint(idPoint);

        BigInteger documentType = BigInteger.ONE;
        voucherSYSType.setDocumentType(documentType);

        voucherSYSType.setCustomerId(billingModel.getCustomerId());
        voucherSYSType.setAccountId(billingModel.getAcctCode());

        voucher.setOriginal(voucherSYSType);

        return voucher;
    }

    /**
     * Método que consume el servicio getVoucher
     *
     * @param idReference
     * @return
     * @throws Exception
     */
    public VoucherResponseType getVoucher(Long idReference, final Map<String, String> parameters) throws Exception {

        VoucherResponseType voucherResponseType = null;

        BillingModel model = this.billingService.findById(idReference);

        if (model == null) {

            throw new BadRequestException(String.format("Error get,Record with id %s is not valid", idReference));

        }

        try {
            URL url = new URL(parameters.get("URL_VOUCHER_SERVICE"));
            // Instanciamos la clase para establecer la conexión al server
            BasicVoucherService basicVoucherService = new BasicVoucherService(url);
            BasicVoucherPort basicVoucherPort = basicVoucherService.getBasicVoucherService();

            // Construimos el request
            GetVoucherType getVoucherType = this.buildGetVoucherType(model.getIdReference(), model);

            // Consumimos el servicio
            voucherResponseType = basicVoucherPort.getVoucher(getVoucherType);

        } catch (WebServiceException e) {

            throw new Exception(e.getMessage());
        } catch (MessageFaultMsg ex) {
            System.out.println(ex.getMessage());
            throw new Exception(ex.getMessage());

        }

        return voucherResponseType;
    }

    /**
     * Método privado que nos ayuda a construir el request para consumir el
     * getVoucher
     *
     * @param model
     * @return
     */
    private GetVoucherType buildGetVoucherType(Long idReference, BillingModel model) {
        GetVoucherType getVoucherType = new GetVoucherType();

        getVoucherType.setIDCompany(model.getIdCompany());
        getVoucherType.setIDSystem(model.getIdSystem());
        getVoucherType.setIDReference(idReference);

        return getVoucherType;
    }

    /**
     * Método que consume el servicio cancelVoucher
     *
     * @param idInvoice
     * @param user
     * @return
     */

    public VoucherACKType cancelVoucher(Long idInvoice, String user, final Map<String, String> parameters, ChannelEntity channelEntity)
            throws MessageFaultMsg {

        VoucherACKType voucherACKType = null;

        BillingModel model = this.billingService.getById(idInvoice);

        if (model == null) {
            throw new BadRequestException(String.format("Error get,Record with id %s is not valid", idInvoice));
        }

        try {
            // Build the cancel voucher request
            CancelVoucherType cancelVoucherType = this.buildCancelVoucherType(model, user);

            // Get the SOAP request with proper headers
            String request = this.cancelVoucherService.getRequest(cancelVoucherType, parameters);

            // Consume the service using VoucherUtil
            String response = this.cancelVoucherService.consumeCancelVoucher(request, parameters);

            // Convert XML response to VoucherACKType
            voucherACKType = this.cancelVoucherService.xmlToVoucherACKType(response);

            if (voucherACKType == null) {
                throw new BadRequestException("Error mapping voucher response");
            }

            if (channelEntity.getLogs() == 1) {
                LogsServiceModel logModel = new LogsServiceModel();
                logModel.setRequest(request);
                logModel.setResponse(response);
                logModel.setReference(idInvoice);
                logModel.setService("CANCEL VOUCHER");
                logModel.setUserCreate(user);
                logModel.setExecutionTime(System.currentTimeMillis());
                logsServicesService.saveLog(logModel);
            }

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

        return voucherACKType;
    }


    /**
     * Método privado que nos ayuda a construir el request para consumir el
     * cancelVoucher
     *
     * @param model
     * @param user
     * @return
     */
    private CancelVoucherType buildCancelVoucherType(BillingModel model, String user) {
        CancelVoucherType cancelVoucherType = new CancelVoucherType();

        cancelVoucherType.setIDCompany(model.getIdCompany());
        cancelVoucherType.setIDSystem(model.getIdSystem());
        cancelVoucherType.setIDVoucher(model.getIdVoucher());
        cancelVoucherType.setIDReference(model.getIdReference());
        cancelVoucherType.setUser(user);
        cancelVoucherType.setTerminal("1");

        return cancelVoucherType;
    }

}
