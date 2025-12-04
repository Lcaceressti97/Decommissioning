package hn.com.tigo.equipmentaccessoriesbilling.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


import hn.com.tigo.equipmentaccessoriesbilling.entities.ChannelEntity;
import hn.com.tigo.equipmentaccessoriesbilling.exceptions.BadRequestException;
import hn.com.tigo.equipmentaccessoriesbilling.models.LogsServiceModel;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.ILogsServicesService;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import hn.com.tigo.equipmentaccessoriesbilling.entities.BillingEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.ConfigParametersModel;
import hn.com.tigo.equipmentaccessoriesbilling.models.PayUpFrontRequest;
import hn.com.tigo.equipmentaccessoriesbilling.models.PayUpFrontRequest.AcctAccessCode;
import hn.com.tigo.equipmentaccessoriesbilling.models.PayUpFrontRequest.PayUpfrontInfo;
import hn.com.tigo.equipmentaccessoriesbilling.provider.PayUpFrontProvider;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IConfigParametersService;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IPayUpFrontService;
import hn.com.tigo.equipmentaccessoriesbilling.services.payupfrontservice.AdapterException_Exception;
import hn.com.tigo.equipmentaccessoriesbilling.services.payupfrontservice.ParameterArray;
import hn.com.tigo.equipmentaccessoriesbilling.services.payupfrontservice.ParameterType;
import hn.com.tigo.equipmentaccessoriesbilling.services.payupfrontservice.TaskRequestType;
import hn.com.tigo.equipmentaccessoriesbilling.services.payupfrontservice.TaskResponseType;

@Service
public class PayUpFrontServiceImpl implements IPayUpFrontService {

    private final IConfigParametersService configParametersService;
    private final ILogsServicesService logsServicesService;

    public PayUpFrontServiceImpl(IConfigParametersService configParametersService, ILogsServicesService logsServicesService) {
        super();
        this.configParametersService = configParametersService;
        this.logsServicesService = logsServicesService;
    }

    @Override
    public TaskResponseType executeTask(BillingEntity billingEntity, ChannelEntity channelEntity) throws AdapterException_Exception {
        try {
            List<ConfigParametersModel> list = this.configParametersService.getByIdApplication(2602L);
            Map<String, String> parameters = new HashMap<>();
            for (ConfigParametersModel parameter : list) {
                parameters.put(parameter.getParameterName(), parameter.getParameterValue());
            }

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

            List<String> cashInvoiceTypes = parametersInvoiceType.get("CASH_INVOICE_TYPE");
            String invoiceType = billingEntity.getInvoiceType();

            PayUpFrontProvider payUpFrontProvider = new PayUpFrontProvider(parameters.get("PAY_UP_FRONT_PROVIDER"));
            String payUpfrontSerialNo = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + billingEntity.getId();
            String transType = parameters.get("TRANS_TYPE");
            String payType = parameters.get("PAY_TYPE");
            String chargeCode = parameters.get("CHARGE_CODE");
            String currencyId = parameters.get("CURRENCY_ID");
            String payUpfrontReason = parameters.get("PAY_UP_FRONT_REASON");
            String dueDateParameter = parameters.get("DUE_DATE");

            String dueDateString;
            if (cashInvoiceTypes.contains(invoiceType)) {
                // Si es factura de contado, usar la fecha actual
                dueDateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            } else {
                // Si es factura al crédito, usar la lógica existente
                LocalDateTime dueDate = LocalDateTime.now().plusDays(Long.parseLong(dueDateParameter));
                dueDateString = dueDate.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            }

            TaskRequestType taskRequestType = new TaskRequestType();

            taskRequestType.setTransactionId(UUID.randomUUID().toString());

            ParameterArray parameterArray = new ParameterArray();
            ParameterType parameterType = new ParameterType();

            // Setea los valores en la instancia de PayUpFrontRequest
            PayUpFrontRequest payUpFrontRequest = new PayUpFrontRequest();
            payUpFrontRequest.setPayUpfrontSerialNo(payUpfrontSerialNo);
            payUpFrontRequest.setTransType(transType);

            AcctAccessCode acctAccessCode = new AcctAccessCode();
            acctAccessCode.setAccountCode(billingEntity.getAcctCode());
            acctAccessCode.setPayType(payType);
            payUpFrontRequest.setAcctAccessCode(acctAccessCode);

            PayUpfrontInfo payUpfrontInfo = new PayUpfrontInfo();
            payUpfrontInfo.setChargeCode(chargeCode);
            payUpfrontInfo.setChargeAmt(Math.round(billingEntity.getAmountTotal() * 1000000));
            payUpfrontInfo.setCurrencyId(Integer.parseInt(currencyId));
            payUpFrontRequest.setPayUpfrontInfo(Collections.singletonList(payUpfrontInfo));

            payUpFrontRequest.setPayUpfrontReason(payUpfrontReason);
            payUpFrontRequest.setDueDate(dueDateString);

            Gson gson = new Gson();
            String json = gson.toJson(payUpFrontRequest);

            parameterType.setName("JSON");

            parameterType.setValue(json);
            parameterArray.getParameter().add(parameterType);
            taskRequestType.setParameters(parameterArray);

            TaskResponseType response = payUpFrontProvider.executeTask(taskRequestType);

            // Creacion de log
            if (channelEntity.getLogs() == 1) {

                LogsServiceModel logModel = new LogsServiceModel();
                logModel.setRequest(json);
                logModel.setResponse(gson.toJson(response));
                logModel.setReference(billingEntity.getId());
                logModel.setService("PAY UP FRONT SERVICE");
                logModel.setUserCreate(billingEntity.getSeller());
                logModel.setExecutionTime(System.currentTimeMillis());

                logsServicesService.saveLog(logModel);
            }


            return response;

        } catch (AdapterException_Exception e) {
            // Propaga con tu BadRequest y el detalle del provider
            String msg = (e.getMessage() != null)
                    ? e.getMessage()
                    : "AdapterException in PayUpFrontProvider";
            throw new BadRequestException("Error Pay Up Front (adapter): " + msg);

        } catch (Exception e) {
            // Nunca retornes null: hazlo visible arriba
            String msg = (e.getMessage() != null)
                    ? e.getMessage()
                    : e.getClass().getSimpleName();
            throw new BadRequestException("Unexpected error in PayUpFrontService: " + msg);
        }
    }

}
