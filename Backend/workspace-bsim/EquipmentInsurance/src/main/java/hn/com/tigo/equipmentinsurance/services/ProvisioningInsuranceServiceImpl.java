package hn.com.tigo.equipmentinsurance.services;

import hn.com.tigo.equipmentinsurance.entities.InsuranceClaimEntity;
import hn.com.tigo.equipmentinsurance.models.ConfigParametersModel;
import hn.com.tigo.equipmentinsurance.provider.NonFiscalNoteProvider;
import hn.com.tigo.equipmentinsurance.services.executeorder.AdditionalParameters;
import hn.com.tigo.equipmentinsurance.services.executeorder.OrderResponse;
import hn.com.tigo.equipmentinsurance.services.executeorder.Parameter;
import hn.com.tigo.equipmentinsurance.services.executeorder.SimpleOrderRequest;
import hn.com.tigo.equipmentinsurance.services.interfaces.IConfigParametersService;
import hn.com.tigo.equipmentinsurance.services.interfaces.IProvisioningInsuranceService;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ProvisioningInsuranceServiceImpl implements IProvisioningInsuranceService {

    private final IConfigParametersService configParametersService;

    public ProvisioningInsuranceServiceImpl(IConfigParametersService configParametersService) {
        this.configParametersService = configParametersService;
    }

    @Override
    public OrderResponse executeTask(InsuranceClaimEntity claim, boolean isCancellation) {
        try {
            List<ConfigParametersModel> list = this.configParametersService.getByIdApplication(2603L);
            Map<String, String> parameters = new HashMap<>();
            for (ConfigParametersModel parameter : list) {
                parameters.put(parameter.getParameterName(), parameter.getParameterValue());
            }

            NonFiscalNoteProvider nonFiscalNoteProvider = new NonFiscalNoteProvider(
                    parameters.get("PROVISIONING_CLAIM_PROVIDER"));

            String channelId = parameters.get("PROVISIONING_CHANNEL_ID");
            String productId = parameters.get("PROVISIONING_PRODUCT_ID");
            String comment = parameters.get("PROVISIONING_COMMENT");
            String quantity = parameters.get("PROVISIONING_QUANTITY");
            String retry = parameters.get("PROVISIONING_REINTENTO");
            String integrationId = parameters.get("PROVISIONING_INTEGRATIONID_SERVICIO");
            String accountName = parameters.get("PROVISIONING_ACCOUNTNAME");
            String quantityParameter = parameters.get("PROVISIONING_QUANTITY_PARAMETER");
            String coverage = parameters.get("PROVISIONING_COBERTURA");


            SimpleOrderRequest simpleOrderRequest = new SimpleOrderRequest();

            simpleOrderRequest.setChannelId(Integer.parseInt(channelId));
            simpleOrderRequest.setSubscriberId(claim.getPhone());
            simpleOrderRequest.setProductId(Long.parseLong(productId));
            simpleOrderRequest.setQuantity(Integer.parseInt(quantity));
            simpleOrderRequest.setExternalTransacionId(UUID.randomUUID().toString());
            simpleOrderRequest.setComment(comment);

            AdditionalParameters additionalParameters = new AdditionalParameters();

            if (isCancellation) {
                additionalParameters.getParameter().add(createParameter("SERIE_NUEVA", claim.getActualEsn()));
                additionalParameters.getParameter().add(createParameter("SERIE_ANTERIOR", claim.getNewEsn()));
                additionalParameters.getParameter().add(createParameter("MODELO_NUEVO", claim.getActualModel()));
                additionalParameters.getParameter().add(createParameter("MODELO_ANTERIOR", claim.getNewModel()));
                additionalParameters.getParameter().add(createParameter("MARCA_ANTERIOR", claim.getNewModel()));
                additionalParameters.getParameter()
                        .add(createParameter("MARCA_NUEVA", claim.getActualModel()));
            } else {
                additionalParameters.getParameter().add(createParameter("SERIE_NUEVA", claim.getNewEsn()));
                additionalParameters.getParameter().add(createParameter("SERIE_ANTERIOR", claim.getActualEsn()));
                additionalParameters.getParameter().add(createParameter("MODELO_NUEVO", claim.getNewModel()));
                additionalParameters.getParameter().add(createParameter("MODELO_ANTERIOR", claim.getActualModel()));
                additionalParameters.getParameter().add(createParameter("MARCA_ANTERIOR", claim.getActualModel()));
                additionalParameters.getParameter()
                        .add(createParameter("MARCA_NUEVA", claim.getNewModel()));

            }

            additionalParameters.getParameter().add(createParameter("REINTENTO", retry));
            additionalParameters.getParameter()
                    .add(createParameter("CUENTA_CLIENTE", claim.getCustomerAccount()));
            additionalParameters.getParameter().add(createParameter("INTEGRATIONID_SERVICIO", integrationId));

            additionalParameters.getParameter().add(createParameter("ACCOUNTNAME", accountName));
            additionalParameters.getParameter().add(createParameter("CUENTA_SERVICIO", claim.getServiceAccount()));
            additionalParameters.getParameter().add(createParameter("CUENTA_FACTURACION", claim.getBillingAccount()));
            additionalParameters.getParameter().add(createParameter("quantity", quantityParameter));
            additionalParameters.getParameter().add(createParameter("COBERTURA", coverage));
            additionalParameters.getParameter().add(createParameter("subscriber", claim.getPhone()));
            simpleOrderRequest.setAdditionalParameters(additionalParameters);

            return nonFiscalNoteProvider.executeTask(simpleOrderRequest);
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