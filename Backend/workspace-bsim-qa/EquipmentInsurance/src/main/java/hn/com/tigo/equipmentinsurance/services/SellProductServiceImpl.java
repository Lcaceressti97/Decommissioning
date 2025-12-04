package hn.com.tigo.equipmentinsurance.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentinsurance.models.ConfigParametersModel;
import hn.com.tigo.equipmentinsurance.models.SellProductModel;
import hn.com.tigo.equipmentinsurance.provider.SellProductProvider;
import hn.com.tigo.equipmentinsurance.services.executeorder.AdditionalParameters;
import hn.com.tigo.equipmentinsurance.services.executeorder.OrderResponse;
import hn.com.tigo.equipmentinsurance.services.executeorder.Parameter;
import hn.com.tigo.equipmentinsurance.services.executeorder.SimpleOrderRequest;
import hn.com.tigo.equipmentinsurance.services.interfaces.IConfigParametersService;
import hn.com.tigo.equipmentinsurance.services.interfaces.ISellProductService;
import lombok.var;

@Service
public class SellProductServiceImpl implements ISellProductService {

	private final IConfigParametersService configParametersService;

	public SellProductServiceImpl(IConfigParametersService configParametersService) {
		super();
		this.configParametersService = configParametersService;
	}

	@Override
	public SellProductModel executeTask(String billingAccount) {
		try {
			List<ConfigParametersModel> list = this.configParametersService.getByIdApplication(7010L);
			Map<String, String> parameters = new HashMap<>();
			for (ConfigParametersModel parameter : list) {
				parameters.put(parameter.getParameterName(), parameter.getParameterValue());
			}

			SellProductProvider sellProductProvider = new SellProductProvider(parameters.get("EXECUTE_ORDER_ENPOINT"));

			String channelId = parameters.get("CHANNEL_ID");
			String subscriberId = parameters.get("SUBSCRIBER_ID");
			String productId = parameters.get("PRODUCT_ID");
			String quantity = parameters.get("QUANTITY");
			String rootAsset = parameters.get("ROOT_ASSET");
			String tipoConsulta = parameters.get("TIPO_CONSULTA");
			String clausulaPermanencia = parameters.get("CLAUSULA_PERMANENCIA");
			String typeAccessCode = parameters.get("TYPE_ACCESS_CODE");

			SimpleOrderRequest simpleOrderRequest = new SimpleOrderRequest();

			simpleOrderRequest.setChannelId(Integer.valueOf(channelId));
			simpleOrderRequest.setSubscriberId(subscriberId);
			simpleOrderRequest.setProductId(Long.parseLong(productId));
			simpleOrderRequest.setQuantity(Integer.valueOf(quantity));
			simpleOrderRequest.setExternalTransacionId(UUID.randomUUID().toString());

			AdditionalParameters additionalParameters = new AdditionalParameters();

			additionalParameters.getParameter().add(createParameter("ROOT_ASSET", rootAsset));
			additionalParameters.getParameter().add(createParameter("ACCESS_CODE", billingAccount));
			additionalParameters.getParameter().add(createParameter("TIPO_CONSULTA", tipoConsulta));
			additionalParameters.getParameter().add(createParameter("TIPO_CONSULTA", clausulaPermanencia));
			additionalParameters.getParameter().add(createParameter("CLAUSULA_PERMANENCIA", clausulaPermanencia));
			additionalParameters.getParameter().add(createParameter("TYPE_ACCESS_CODE", typeAccessCode));

			simpleOrderRequest.setAdditionalParameters(additionalParameters);

			OrderResponse orderResponse = sellProductProvider.executeTask(simpleOrderRequest);

			// Extraer el valor de ASSETSTATUS
			String assetStatus = null;
			if (orderResponse != null && orderResponse.getOrderResponseDetail() != null) {
				for (var detail : orderResponse.getOrderResponseDetail()) {
					for (var parameter : detail.getParameters().getParameter()) {
						if ("ASSETSTATUS".equals(parameter.getName())) {
							assetStatus = parameter.getValue();
							break;
						}
					}
				}
			}

			return new SellProductModel(assetStatus);
		} catch (BadRequestException e) {
			System.err.println("BadRequestException: " + e.getMessage());

			throw new BadRequestException(e.getMessage());

		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
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
