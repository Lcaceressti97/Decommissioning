package hn.com.tigo.jteller.provider;

import java.util.ArrayList;
import java.util.List;


import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import hn.com.tigo.jteller.mapper.cbsquery.invoice.enhanced.*;
import hn.com.tigo.jteller.utils.InvoiceInfoDeserializer;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import hn.com.tigo.jteller.dto.BillingDocument;
import hn.com.tigo.jteller.dto.BillingInfo;
import hn.com.tigo.jteller.exceptions.BadRequestException;
import hn.com.tigo.jteller.models.AcctAccessCode;
import hn.com.tigo.jteller.models.QueryObj;
import hn.com.tigo.jteller.services.soap.cbsquery.invoice.enhanced.AdapterException_Exception;
import hn.com.tigo.jteller.services.soap.cbsquery.invoice.enhanced.CBSQueryInvoiceEnhancedTask;
import hn.com.tigo.jteller.services.soap.cbsquery.invoice.enhanced.CBSQueryInvoiceEnhancedTaskService;
import hn.com.tigo.jteller.services.soap.cbsquery.invoice.enhanced.ParameterArray;
import hn.com.tigo.jteller.services.soap.cbsquery.invoice.enhanced.ParameterType;
import hn.com.tigo.jteller.services.soap.cbsquery.invoice.enhanced.TaskRequestType;
import hn.com.tigo.jteller.services.soap.cbsquery.invoice.enhanced.TaskResponseType;
import hn.com.tigo.jteller.utils.Util;

@Service
public class InvoiceEnhancedProvider {

    // Props
    private Util util;

    public InvoiceEnhancedProvider() {
        this.util = new Util();
    }

    public BillingInfo getInvoiceInfo(final String accountCode) {

        // Instanciamos y seteamos la clase de retorno con sus valores por defecto
        List<BillingDocument> billingDocuments = new ArrayList<>();
        BillingDocument billingDocument = new BillingDocument();
        billingDocument.setExchangeRate("");
        billingDocument.setInvoiceAmount("");
        billingDocument.setInvoicePre("0");
        billingDocument.setOpenAmount("");
        billingDocument.setOpenTaxAmount("");
        billingDocument.setTaxAmount("");
        billingDocument.setTransType("");

        BillingInfo billingInfo = new BillingInfo();
        billingInfo.setTotalDocuments(0);
        billingInfo.setTotalOpenAmount("0");
        billingInfo.setCurrency("");
        billingInfo.setBillingDocuments(billingDocuments);

        // Instanciamos la conexión al servicio
        CBSQueryInvoiceEnhancedTaskService cBSQueryInvoiceEnhancedTaskService = new CBSQueryInvoiceEnhancedTaskService();

        CBSQueryInvoiceEnhancedTask cBSQueryInvoiceEnhancedTask = cBSQueryInvoiceEnhancedTaskService
                .getCBSQueryInvoiceEnhancedTaskPort();

        try {

            // Ejecutamos el método del servicio
            TaskResponseType taskResponseType = cBSQueryInvoiceEnhancedTask
                    .executeTask(this.buildTaskRequestTypeByAccountCode(accountCode));

            // Objeto para mapear a clase la respuesta
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(new TypeToken<List<ArsInvoiceInfo>>() {
                    }.getType(), new InvoiceInfoDeserializer())
                    .create();

            // Variable de mapeo de clases, una trabaja con Arreglos y la otra no
            InvoiceEnhanced mapper = null;
            InvoiceEnhancedArray mapper2 = null;

            // Bucle que recorre la respuesta del servicio
            for (ParameterType row : taskResponseType.getParameters().getParameter()) {

                // Condición para ver si uno, más de uno o ningún registro en el response
                if (row.getValue().contains("\"ars:TotalRowNum\":0")) {
                    mapper = null;
                } else {
                    if (row.getValue().contains("\"ars:TotalRowNum\":1")) {
                        mapper = gson.fromJson(row.getValue(), InvoiceEnhanced.class);
                    } else {
                        mapper2 = gson.fromJson(row.getValue(), InvoiceEnhancedArray.class);

                    }
                }

                break;
            }


            // Condición para extraer los datos si es un objeto la respuesta
            if (mapper != null) {
                List<ArsInvoiceInfo> invoiceInfoList = mapper.getArsQueryInvoiceEnhancedResultMsg()
                        .getQueryInvoiceEnhancedResult().getArsInvoiceInfo();

                for (ArsInvoiceInfo invoiceInfo : invoiceInfoList) {
                    BillingDocument newBillingDocument = new BillingDocument();

                    newBillingDocument.setInvoicePre(invoiceInfo.getArsInvoiceNo());

                    Long openA = invoiceInfo.getArsOpenAmount();
                    Double openAmount = openA.doubleValue() / 1000000;
                    newBillingDocument.setOpenAmount(openAmount.toString());
                    billingInfo.setTotalOpenAmount(openAmount.toString());

                    Long invoiceAmount = invoiceInfo.getArsInvoiceAmount();
                    Double invoiceA = invoiceAmount.doubleValue() / 1000000;
                    newBillingDocument.setInvoiceAmount(invoiceA.toString());

                    Long openTaxAmount = invoiceInfo.getArsOpenTaxAmount();
                    Double openTaxA = openTaxAmount.doubleValue() / 1000000;
                    newBillingDocument.setOpenTaxAmount(openTaxA.toString());

                    Long taxAmount = invoiceInfo.getArsTAXAmount();
                    Double taxA = taxAmount.doubleValue() / 1000000;
                    newBillingDocument.setTaxAmount(taxA.toString());

                    newBillingDocument.setTransType(invoiceInfo.getArsTransType());

                    // Handle arsAdditionalProperty and exchange rate as needed
                    Object additionalProperty = invoiceInfo.getArsAdditionalProperty();
                    if (additionalProperty != null) {
                        String additionalPropertyStr = additionalProperty.toString();

                        // Check if additionalProperty is an array
                        if (additionalPropertyStr.contains("[")) {
                            // Extract exchange rate from array
                            String[] arrayString = additionalPropertyStr.split("},");

                            for (String cadena : arrayString) {
                                if (cadena.contains("ExchangeRate")) {
                                    int inicio = cadena.indexOf("arc:Value=");
                                    String ca = cadena.substring(inicio + "arc:Value=".length());
                                    String[] finalString = ca.split(",");
                                    newBillingDocument.setExchangeRate(finalString[0]);
                                }
                            }
                        } else {
                            // Handle as a single object
                            String text = additionalPropertyStr;
                            String jsonString = text.replaceAll("\\{([^=]+)=([^,]+),\\s([^=]+)=([^}]+)\\}",
                                    "{\"$1\":\"$2\", \"$3\":\"$4\"}");

                            // Deserialize to ArsAdditionalProperty
                            ArsAdditionalProperty additionalPropertyObj = gson.fromJson(jsonString, ArsAdditionalProperty.class);
                            newBillingDocument.setExchangeRate(additionalPropertyObj.getArcValue());
                        }
                    }

                    // Setear valores principales
                    billingInfo.setTotalDocuments(
                            mapper.getArsQueryInvoiceEnhancedResultMsg().getQueryInvoiceEnhancedResult().getTotalRowNum());
                    billingInfo.setCurrency(invoiceInfo.getArsCurrencyID().toString().equals("1153") ? "USD" : "LPS");

                    // Set dates
                    String invoiceDate = Util.formatDate(invoiceInfo.getArsInvoiceDate());
                    String dueDate = Util.formatDate(invoiceInfo.getArsDueDate());
                    newBillingDocument.setInvoiceDate(invoiceDate);
                    newBillingDocument.setDueDate(dueDate);

                    billingDocuments.add(newBillingDocument);
                }

                billingInfo.setBillingDocuments(billingDocuments);
            }
            // Este condicional nos ayuda a construir las facturas, obviamente, funciona si
            // hay más de una factura
            if (mapper2 != null) {

                // Seteamos valores
                billingInfo.setTotalDocuments(mapper2.getArsQueryInvoiceEnhancedResultMsg()
                        .getQueryInvoiceEnhancedResultArray().getTotalRowNum());

                Double totalOpenA = 0.00;
                // Bucle que nos ayuda a recorrer cada información de cada factura
                for (ArsInvoiceInfoArray param : mapper2.getArsQueryInvoiceEnhancedResultMsg()
                        .getQueryInvoiceEnhancedResultArray().getInvoiceInfo()) {

                    BillingDocument billingDoc = new BillingDocument();

                    billingDoc.setInvoicePre(param.getArsInvoiceNo().toString());
                    Long openA = param.getArsOpenAmount();
                    Double openAmount = openA.doubleValue();
                    openAmount = openAmount / 1000000;

                    billingDoc.setOpenAmount(openAmount.toString());
                    Long invoiceAmount = param.getArsInvoiceAmount();
                    Double invoiceA = invoiceAmount.doubleValue();
                    invoiceA = invoiceA / 1000000;
                    billingDoc.setInvoiceAmount(invoiceA.toString());

                    Long openTaxtAmount = param.getArsOpenTaxAmount();
                    Double openTaxtA = openTaxtAmount.doubleValue();
                    openTaxtA = openTaxtA / 1000000;
                    billingDoc.setOpenTaxAmount(openTaxtA.toString());

                    Long taxtAmount = param.getArsTAXAmount();
                    Double taxtA = taxtAmount.doubleValue();
                    taxtA = taxtA / 1000000;
                    billingDoc.setTaxAmount(taxtA.toString());

                    // Agregamos el acumulador
                    totalOpenA = totalOpenA + openAmount;

                    // Agregamos las fechas
                    String invoiceDate = Util.formatDate(param.getArsInvoiceDate());


                    String dueDate = Util.formatDate(param.getArsDueDate());
                    billingDoc.setInvoiceDate(invoiceDate);
                    billingDoc.setDueDate(dueDate);

                    // Agregamos el valor de la tasa de cambio
                    if (param.getArsAdditionalProperty().toString().contains("[")) {

                        String[] arrayString = param.getArsAdditionalProperty().toString().split("},");

                        for (String cadena : arrayString) {

                            if (cadena.contains("ExchangeRate")) {
                                int inicio = cadena.indexOf("arc:Value=");
                                String ca = cadena.substring(inicio + "arc:Value=".length());

                                String[] finalString = ca.split(",");
                                billingDoc.setExchangeRate(finalString[0]);
                            }
                        }

                    } else {
                        String text = param.getArsAdditionalProperty().toString();
                        String jsonString = text.replaceAll("\\{([^=]+)=([^,]+),\\s([^=]+)=([^}]+)\\}",
                                "{\"$1\":\"$2\", \"$3\":\"$4\"}");

                        ArsAdditionalProperty additionalProperty = gson.fromJson(jsonString,
                                ArsAdditionalProperty.class);

                        billingDoc.setExchangeRate(additionalProperty.getArcValue());
                    }

                    billingDoc.setTransType(param.getArsTransType());
                    billingDocuments.add(billingDoc);

                }

                billingInfo.setTotalOpenAmount(totalOpenA.toString());
                billingInfo
                        .setCurrency(mapper2.getArsQueryInvoiceEnhancedResultMsg().getQueryInvoiceEnhancedResultArray()
                                .getInvoiceInfo().get(0).getArsCurrencyID().toString().equals("1153") ? "USD" : "LPS");
            }

            return billingInfo;

        } catch (AdapterException_Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }

    /**
     * Método que construye el request para el consumo del servicio
     *
     * @param accountCode
     * @return
     */
    private TaskRequestType buildTaskRequestTypeByAccountCode(final String accountCode) {

        // Instanciamos el objeto de retorno
        TaskRequestType taskRequestType = new TaskRequestType();

        // Instanciamos las clase que representan el JSON
        ParameterType parameterType = new ParameterType();
        ParameterArray parameterArray = new ParameterArray();

        // Seteamos los objetos del request
        RequestInvoiceEnhanced requestInvoiceEnhanced = new RequestInvoiceEnhanced();
        requestInvoiceEnhanced.setBeginRowNum("0");
        requestInvoiceEnhanced.setFetchRowNum("0");
        requestInvoiceEnhanced.setTotalRowNum("100");
        requestInvoiceEnhanced.setRetrieveDetail("1");

        InvoiceHeaderFilter invoiceHeaderFilter = new InvoiceHeaderFilter();
        invoiceHeaderFilter.setStatus("O");

        QueryObj queryObj = new QueryObj();

        AcctAccessCode acctAccessCode = new AcctAccessCode();
        acctAccessCode.setAccountCode(accountCode);
        acctAccessCode.setPayType("2");

        queryObj.setAcctAccessCode(acctAccessCode);

        requestInvoiceEnhanced.setQueryObj(queryObj);
        requestInvoiceEnhanced.setInvoiceHeaderFilter(invoiceHeaderFilter);

        // Crear un objeto Gson
        Gson gson = new Gson();

        // Convertir la instancia de la clase QueryObj a una cadena con formato JSON
        String json = gson.toJson(requestInvoiceEnhanced);

        parameterType.setName("JSON");
        parameterType.setValue(json);

        parameterArray.getParameter().add(parameterType);

        taskRequestType.setParameters(parameterArray);


        return taskRequestType;
    }

}
