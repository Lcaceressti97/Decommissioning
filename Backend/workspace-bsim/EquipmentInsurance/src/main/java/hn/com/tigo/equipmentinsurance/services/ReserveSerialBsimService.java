package hn.com.tigo.equipmentinsurance.services;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import hn.com.tigo.equipmentinsurance.models.UnloadReservedStockBsimModel;
import hn.com.tigo.equipmentinsurance.entities.InvoiceDetailEntity;
import hn.com.tigo.equipmentinsurance.models.ConfigParametersModel;
import hn.com.tigo.equipmentinsurance.models.ReserveSerialBsimModel;
import hn.com.tigo.equipmentinsurance.services.interfaces.IConfigParametersService;

@Service
public class ReserveSerialBsimService {

	private final IConfigParametersService configParametersService;

	public ReserveSerialBsimService(IConfigParametersService configParametersService) {
		super();
		this.configParametersService = configParametersService;
	}

	public ReserveSerialBsimModel reserveSeries(String accessToken, String inventoryType, String itemCode,
			String warehouseCode, String subWarehouseCode, List<InvoiceDetailEntity> invoiceDetails) {

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

		String reserveSeriesUrl = paramsAuthentication.get("RESERVE_SERIAL_ENDPOINT");

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("inventory_type", inventoryType);
		requestBody.put("item_code", itemCode);
		requestBody.put("warehouse_code", warehouseCode);
		requestBody.put("sub_warehouse_code", subWarehouseCode);

		List<Map<String, String>> serialNumberList = invoiceDetails.stream().map(detail -> {
			Map<String, String> serialNumberMap = new HashMap<>();
			serialNumberMap.put("serial_number", detail.getSerieOrBoxNumber());
			return serialNumberMap;
		}).collect(Collectors.toList());
		requestBody.put("serial_number_list", serialNumberList);

		HttpEntity<Object> request = new HttpEntity<>(requestBody, headers);
		System.out.println("REQUEST RESERVA DE SERIES:" + request);

		ResponseEntity<String> response = restTemplate.exchange(reserveSeriesUrl, HttpMethod.POST, request,
				String.class);

		Gson gson = new Gson();
		Type reservationResponseType = new TypeToken<ReserveSerialBsimModel>() {
		}.getType();
		ReserveSerialBsimModel reserveSerialResponse = gson.fromJson(response.getBody(), reservationResponseType);

		if (reserveSerialResponse.getResult_code().endsWith("000")) {
			return reserveSerialResponse;
		} else {
			throw new BadRequestException("Error when booking series: " + reserveSerialResponse.getResult_message()
					+ " - " + reserveSerialResponse.getReservation_result_list().get(0).getReservation_result());
		}

	}
	
	public UnloadReservedStockBsimModel unloadReservedStock(String accessToken, String inventoryType, String itemCode,
			String warehouseCode, String subWarehouseCode, String reserveKey, List<InvoiceDetailEntity> invoiceDetails) {

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

		String unloadStockUrl = paramsAuthentication.get("UNLOAD_STOCK_R_ENDPOINT");
		String transactionType = paramsAuthentication.get("TRANSACTION_TYPE_UNLOAD_STOCK");

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("transaction_type", transactionType);
		requestBody.put("inventory_type", inventoryType);
		requestBody.put("item_code", itemCode);
		requestBody.put("warehouse_code", warehouseCode);
		requestBody.put("sub_warehouse_code", subWarehouseCode);
		requestBody.put("reserve_key", reserveKey);

		List<Map<String, String>> serialNumberList = invoiceDetails.stream().map(detail -> {
			Map<String, String> serialNumberMap = new HashMap<>();
			serialNumberMap.put("serial_number", detail.getSerieOrBoxNumber());
			return serialNumberMap;
		}).collect(Collectors.toList());
		requestBody.put("serial_number_list", serialNumberList);

		HttpEntity<Object> request = new HttpEntity<>(requestBody, headers);
		System.out.println("--- REQUEST UNLOAD RESERVE STOCK ---");
		System.out.println(requestBody);
		ResponseEntity<String> response = restTemplate.exchange(unloadStockUrl, HttpMethod.POST, request, String.class);
		System.out.println("--- RESPONSE UNLOAD RESERVE STOCK ---");
		System.out.println(response.getBody());
		Gson gson = new Gson();
		Type unloadStockResponseType = new TypeToken<UnloadReservedStockBsimModel>() {
		}.getType();
		UnloadReservedStockBsimModel unloadStockResponse = gson.fromJson(response.getBody(), unloadStockResponseType);

		if (unloadStockResponse.getResult_code().endsWith("000")) {
			return unloadStockResponse;
		} else {
			throw new BadRequestException("Error unload stock: " + unloadStockResponse.getResult_message());
		}

	}
}