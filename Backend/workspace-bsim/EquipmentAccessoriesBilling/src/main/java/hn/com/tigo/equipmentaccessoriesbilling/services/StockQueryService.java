package hn.com.tigo.equipmentaccessoriesbilling.services;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.BadRequestException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import hn.com.tigo.equipmentaccessoriesbilling.models.ConfigParametersModel;
import hn.com.tigo.equipmentaccessoriesbilling.models.StockQueryModel;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IConfigParametersService;

@Service
public class StockQueryService {

	private final IConfigParametersService configParametersService;

	public StockQueryService(IConfigParametersService configParametersService) {
		super();
		this.configParametersService = configParametersService;
	}

	public StockQueryModel stockQuery(String accessToken, String inventoryType, String itemCode,
			String warehouseCode, String subWarehouseCode) {

		List<ConfigParametersModel> listAuthentication = this.configParametersService.getByIdApplication(30410L);
		Map<String, List<String>> parametersBsim = new HashMap<>();
		Map<String, String> paramsAuthentication = new HashMap<>();

		for (ConfigParametersModel parameter : listAuthentication) {
			String paramName = parameter.getParameterName();
			String paramValue = parameter.getParameterValue();

			if (!parametersBsim.containsKey(paramName)) {
				parametersBsim.put(paramName, new ArrayList<>());
			}

			parametersBsim.get(paramName).add(paramValue);
			paramsAuthentication.put(paramName, paramValue);
		}

		String stockQueryUrl = paramsAuthentication.get("STOCK_QUERY_ENDPOINT");

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("inventory_type", inventoryType);
		requestBody.put("item_code", itemCode);
		requestBody.put("warehouse_code", warehouseCode);
		requestBody.put("sub_warehouse_code", subWarehouseCode);

		HttpEntity<Object> request = new HttpEntity<>(requestBody, headers);

		ResponseEntity<String> response = restTemplate.exchange(stockQueryUrl, HttpMethod.POST, request, String.class);

		Gson gson = new Gson();
		Type stockResponseType = new TypeToken<StockQueryModel>() {
		}.getType();
		StockQueryModel stockQueryResponse = gson.fromJson(response.getBody(), stockResponseType);

		if (stockQueryResponse.getResult_code().endsWith("000")) {
			return stockQueryResponse;
		} else {
			throw new BadRequestException("Error when stock query: " + stockQueryResponse.getResult_message());
		}

	}

}
