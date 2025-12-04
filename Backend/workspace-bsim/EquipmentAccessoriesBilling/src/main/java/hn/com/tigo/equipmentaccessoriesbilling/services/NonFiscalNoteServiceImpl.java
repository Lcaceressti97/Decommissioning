package hn.com.tigo.equipmentaccessoriesbilling.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.BadRequestException;

import com.google.gson.Gson;
import hn.com.tigo.equipmentaccessoriesbilling.entities.ChannelEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.LogsServiceModel;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.ILogsServicesService;
import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentaccessoriesbilling.entities.BillingEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.ConfigParametersModel;
import hn.com.tigo.equipmentaccessoriesbilling.models.ControlAuthEmissionModel;
import hn.com.tigo.equipmentaccessoriesbilling.provider.NonFiscalNoteProvider;
import hn.com.tigo.equipmentaccessoriesbilling.services.executeorderservice.AdditionalParameters;
import hn.com.tigo.equipmentaccessoriesbilling.services.executeorderservice.OrderResponse;
import hn.com.tigo.equipmentaccessoriesbilling.services.executeorderservice.Parameter;
import hn.com.tigo.equipmentaccessoriesbilling.services.executeorderservice.SimpleOrderRequest;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IConfigParametersService;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.INonFiscalNoteService;

@Service
public class NonFiscalNoteServiceImpl implements INonFiscalNoteService {

    private final IConfigParametersService configParametersService;
    private final ILogsServicesService logsServicesService;

    public NonFiscalNoteServiceImpl(IConfigParametersService configParametersService, ILogsServicesService logsServicesService) {
        super();
        this.configParametersService = configParametersService;
        this.logsServicesService = logsServicesService;
    }

    @Override
    public OrderResponse executeTask(BillingEntity billingEntity, ControlAuthEmissionModel model, ChannelEntity channelEntity) {
        try {
            List<ConfigParametersModel> list = this.configParametersService.getByIdApplication(2603L);
            Map<String, String> parameters = new HashMap<>();
            for (ConfigParametersModel parameter : list) {
                parameters.put(parameter.getParameterName(), parameter.getParameterValue());
            }

            NonFiscalNoteProvider nonFiscalNoteProvider = new NonFiscalNoteProvider(
                    parameters.get("NON_FISCAL_NOTE_PROVIDER"));

            String channelId = parameters.get("CHANNEL_ID");
            String comment = parameters.get("COMMENT");
            String remarksParam = parameters.get("REMARKS");
            String type = parameters.get("TYPE");
            String operation = parameters.get("OPERATION");
            String motivo = parameters.get("MOTIVO");
            String productId = parameters.get("PRODUCT_ID");

            String remarks = remarksParam + UUID.randomUUID().toString() + ";" + model.getUserCreate();

            SimpleOrderRequest simpleOrderRequest = new SimpleOrderRequest();

            simpleOrderRequest.setChannelId(Integer.valueOf(channelId));
            simpleOrderRequest.setSubscriberId(billingEntity.getAcctCode());
            simpleOrderRequest.setProductId(Long.parseLong(productId));
            simpleOrderRequest.setQuantity(1);
            simpleOrderRequest.setExternalTransacionId(UUID.randomUUID().toString());
            simpleOrderRequest.setComment(comment);

            AdditionalParameters additionalParameters = new AdditionalParameters();

            additionalParameters.getParameter().add(createParameter("APROBADOR", model.getUserCreate()));
            additionalParameters.getParameter().add(createParameter("CREADOR", model.getUserCreate()));
            additionalParameters.getParameter().add(createParameter("REMARKS", remarks));
            additionalParameters.getParameter()
                    .add(createParameter("SUBPRIMARY_IDENTITY", billingEntity.getAcctCode()));
            additionalParameters.getParameter().add(createParameter("ACCOUNT", billingEntity.getAcctCode()));
            additionalParameters.getParameter()
                    .add(createParameter("AMOUNT", String.valueOf(billingEntity.getAmountTotal())));
            additionalParameters.getParameter().add(createParameter("TYPE", type));
            additionalParameters.getParameter().add(createParameter("OPERATION", operation));

            additionalParameters.getParameter().add(createParameter("MOTIVO", motivo));

            simpleOrderRequest.setAdditionalParameters(additionalParameters);


            OrderResponse response = nonFiscalNoteProvider.executeTask(simpleOrderRequest);

            // Creacion de log
            if (channelEntity.getLogs() == 1) {
                LogsServiceModel logModel = new LogsServiceModel();
                logModel.setRequest(new Gson().toJson(simpleOrderRequest));
                logModel.setResponse(new Gson().toJson(response));
                logModel.setReference(model.getIdPrefecture());
                logModel.setService("FISCAL NOTE SERVICE");
                logModel.setUserCreate(model.getUserCreate());
                logModel.setExecutionTime(System.currentTimeMillis());

                logsServicesService.saveLog(logModel);
            }


            return response;

        } catch (BadRequestException e) {

            throw new BadRequestException(e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Parameter createParameter(String key, String value) {
        Parameter parameter = new Parameter();
        parameter.setKey(key);
        parameter.setValue(value);
        return parameter;
    }

}
