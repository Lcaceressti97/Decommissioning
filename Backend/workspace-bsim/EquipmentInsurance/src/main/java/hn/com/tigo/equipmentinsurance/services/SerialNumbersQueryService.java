package hn.com.tigo.equipmentinsurance.services;

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

import hn.com.tigo.equipmentinsurance.dto.SerialNumberWithReservationDTO;
import hn.com.tigo.equipmentinsurance.models.AuthenticationBsimResponse;
import hn.com.tigo.equipmentinsurance.models.ConfigParametersModel;
import hn.com.tigo.equipmentinsurance.models.SerialNumbersQueryModel;
import hn.com.tigo.equipmentinsurance.models.SerialNumbersQueryReservationModel;
import hn.com.tigo.equipmentinsurance.services.interfaces.IConfigParametersService;

@Service
public class SerialNumbersQueryService {

	private final IConfigParametersService configParametersService;
	private final AuthenticationBsimService authenticationService;

	public SerialNumbersQueryService(IConfigParametersService configParametersService, AuthenticationBsimService authenticationService) {
		super();
		this.configParametersService = configParametersService;
		this.authenticationService = authenticationService;

	}

	public SerialNumbersQueryModel serialNumbersQuery(String itemCode, String warehouseCode,
			String subWarehouseCode, String inventoryType) {

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
		
		AuthenticationBsimResponse accessToken = authenticationService.getAccessToken();
		String stockQueryUrl = paramsAuthentication.get("SERIAL_NUMBERS_QUERY_ENDPOINT");

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken.getAccess_token());
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("item_code", itemCode);
		requestBody.put("warehouse_code", warehouseCode);
		requestBody.put("sub_warehouse_code", subWarehouseCode);
		requestBody.put("inventory_type", inventoryType);

		HttpEntity<Object> request = new HttpEntity<>(requestBody, headers);

		ResponseEntity<String> response = restTemplate.exchange(stockQueryUrl, HttpMethod.POST, request, String.class);

		Gson gson = new Gson();
		Type serialNumbersResponseType = new TypeToken<SerialNumbersQueryModel>() {
		}.getType();
		SerialNumbersQueryModel serialNumbersResponse = gson.fromJson(response.getBody(), serialNumbersResponseType);

		if (serialNumbersResponse.getResult_code().endsWith("000")) {
			return serialNumbersResponse;
		} else {
			throw new BadRequestException("Error when stock query: " + serialNumbersResponse.getResult_message());
		}

	}
	
	public SerialNumbersQueryReservationModel serialNumbersQueryWithReservation(SerialNumberWithReservationDTO dto) {

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
		
		AuthenticationBsimResponse accessToken = authenticationService.getAccessToken();
		String stockQueryUrl = paramsAuthentication.get("SERIAL_NUMBERS_QUERY_R_ENDPOINT");

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken.getAccess_token());
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("item_code", dto.getItemCode());
		requestBody.put("warehouse_code", dto.getWarehouseCode());
		requestBody.put("sub_warehouse_code", dto.getSubWarehouseCode());
		requestBody.put("inventory_type", dto.getInventoryType());
		requestBody.put("row_limit", dto.getRowLimit());

		HttpEntity<Object> request = new HttpEntity<>(requestBody, headers);
		ResponseEntity<String> response = restTemplate.exchange(stockQueryUrl, HttpMethod.POST, request, String.class);

		Gson gson = new Gson();
		Type serialNumbersResponseType = new TypeToken<SerialNumbersQueryReservationModel>() {
		}.getType();
		SerialNumbersQueryReservationModel serialNumbersResponse = gson.fromJson(response.getBody(), serialNumbersResponseType);

		if (serialNumbersResponse.getResult_code().endsWith("000")) {
			return serialNumbersResponse;
		} else {
			throw new BadRequestException("Error in serialNumbersQueryWithReservation: " + serialNumbersResponse.getResult_message());
		}

	}
}
