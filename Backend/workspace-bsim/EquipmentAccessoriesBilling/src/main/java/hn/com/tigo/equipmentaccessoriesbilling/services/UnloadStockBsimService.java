package hn.com.tigo.equipmentaccessoriesbilling.services;

import java.lang.reflect.Type;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import hn.com.tigo.equipmentaccessoriesbilling.entities.ChannelEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.*;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IControlUnloadStockService;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.ILogsServicesService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import hn.com.tigo.equipmentaccessoriesbilling.entities.InvoiceDetailEntity;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IConfigParametersService;

@Service
public class UnloadStockBsimService {

    private final IConfigParametersService configParametersService;
    private final ILogsServicesService logsServicesService;
    private final IControlUnloadStockService controlUnloadStockService;

    public UnloadStockBsimService(IConfigParametersService configParametersService,
                                  ILogsServicesService logsServicesService, IControlUnloadStockService controlUnloadStockService) {
        super();
        this.configParametersService = configParametersService;
        this.logsServicesService = logsServicesService;
        this.controlUnloadStockService = controlUnloadStockService;
    }

    public UnloadStockBsimModel unloadStock(String accessToken, String inventoryType, String itemCode,
                                            String warehouseCode, String subWarehouseCode, List<InvoiceDetailEntity> invoiceDetails, Long reference,
                                            String userCreate, ChannelEntity channelEntity) {

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

        String unloadStockUrl = paramsAuthentication.get("UNLOAD_STOCK_ENDPOINT");
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

        List<Map<String, String>> serialNumberList = invoiceDetails.stream().map(detail -> {
            Map<String, String> serialNumberMap = new HashMap<>();
            serialNumberMap.put("serial_number", detail.getSerieOrBoxNumber());
            return serialNumberMap;
        }).collect(Collectors.toList());
        requestBody.put("serial_number_list", serialNumberList);

        HttpEntity<Object> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(unloadStockUrl, HttpMethod.POST, request, String.class);

        Gson gson = new Gson();
        Type unloadStockResponseType = new TypeToken<UnloadStockBsimModel>() {
        }.getType();
        UnloadStockBsimModel unloadStockResponse = gson.fromJson(response.getBody(), unloadStockResponseType);

        // Creacion de log
        if (channelEntity.getLogs() == 1) {
            LogsServiceModel logModel = new LogsServiceModel();
            logModel.setRequest(new Gson().toJson(requestBody));
            logModel.setResponse(response.getBody());
            logModel.setReference(reference);
            logModel.setService("UNLOAD STOCK SERVICE");
            logModel.setUserCreate(userCreate);
            logModel.setExecutionTime(System.currentTimeMillis());

            logsServicesService.saveLog(logModel);
        }

        if (unloadStockResponse.getResult_code().endsWith("000")) {
            return unloadStockResponse;
        } else {
            throw new BadRequestException("Error unload stock: " + unloadStockResponse.getResult_message());
        }

    }

    public UnloadReservedStockBsimModel unloadReservedStock(String accessToken, String inventoryType, String itemCode,
                                                            String warehouseCode, String subWarehouseCode, String reserveKey,
                                                            List<InvoiceDetailEntity> invoiceDetails, Long reference,
                                                            String userCreate, ChannelEntity channelEntity) {

        long start = System.currentTimeMillis();

        List<ConfigParametersModel> listAuthentication = this.configParametersService.getByIdApplication(30410L);
        Map<String, String> paramsAuthentication = new HashMap<>();
        for (ConfigParametersModel parameter : listAuthentication) {
            paramsAuthentication.put(parameter.getParameterName(), parameter.getParameterValue());
        }

        //String unloadStockUrl = paramsAuthentication.get("UNLOAD_STOCK_R_ENDPOINT");
		// Quitar para QA
		String unloadStockUrl = "https://bsim.tigo.com.hn/api/bsim-operations-service/v1/operations/unloadReservedStock";
		String transactionType = paramsAuthentication.get("TRANSACTION_TYPE_UNLOAD_STOCK");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

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

        unloadStockUrl = unloadStockUrl.trim();
        URI uri = URI.create(unloadStockUrl);

        ResponseEntity<String> response = null;
        String responseBody = null;

        try {
            response = restTemplate.exchange(uri, HttpMethod.POST, request, String.class);
            responseBody = response.getBody();

            Gson gson = new Gson();
            Type unloadStockResponseType = new TypeToken<UnloadReservedStockBsimModel>() {
            }.getType();
            UnloadReservedStockBsimModel unloadStockResponse = gson.fromJson(responseBody, unloadStockResponseType);

            // logs
            if (channelEntity.getLogs() == 1) {
                LogsServiceModel logModel = new LogsServiceModel();
                logModel.setRequest(new Gson().toJson(requestBody));
                logModel.setResponse(responseBody);
                logModel.setReference(reference);
                logModel.setService("UNLOAD RESERVE STOCK SERVICE");
                logModel.setUserCreate(userCreate);
                logModel.setExecutionTime(System.currentTimeMillis());
                logsServicesService.saveLog(logModel);
            }

            if (unloadStockResponse != null
                    && unloadStockResponse.getResult_code() != null
                    && unloadStockResponse.getResult_code().endsWith("000")) {
                return unloadStockResponse;
            } else {
                String msg = (unloadStockResponse != null) ? unloadStockResponse.getResult_message() : "null response";

                persistFailure(buildFailureModel(
                        reference, userCreate,
                        "UNLOAD_RESERVED_STOCK", unloadStockUrl,
                        inventoryType, itemCode, warehouseCode, subWarehouseCode, reserveKey,
                        new Gson().toJson(requestBody), responseBody,
                        "BSIM_ERROR", "Error unload stock: " + msg,
                        null, System.currentTimeMillis() - start
                ));

                throw new BadRequestException("Error unload stock: " + msg);

            }


        } catch (HttpStatusCodeException hosted) {
            responseBody = hosted.getResponseBodyAsString();
            persistFailure(buildFailureModel(
                    reference, userCreate,
                    "UNLOAD_RESERVED_STOCK", unloadStockUrl,
                    inventoryType, itemCode, warehouseCode, subWarehouseCode, reserveKey,
                    new Gson().toJson(requestBody), responseBody,
                    "HTTP_" + hosted.getStatusCode().value(),
                    "HTTP error calling UNLOAD_RESERVED_STOCK: " + hosted.getMessage(),
                    hosted, System.currentTimeMillis() - start
            ));
            throw hosted;

        } catch (ResourceAccessException rae) {
            persistFailure(buildFailureModel(
                    reference, userCreate,
                    "UNLOAD_RESERVED_STOCK", unloadStockUrl,
                    inventoryType, itemCode, warehouseCode, subWarehouseCode, reserveKey,
                    new Gson().toJson(requestBody), responseBody,
                    "CONNECTION",
                    "Connection/timeout calling UNLOAD_RESERVED_STOCK: " + rae.getMessage(),
                    rae, System.currentTimeMillis() - start
            ));
            throw rae;

        } catch (Exception ex) {
            persistFailure(buildFailureModel(
                    reference, userCreate,
                    "UNLOAD_RESERVED_STOCK", unloadStockUrl,
                    inventoryType, itemCode, warehouseCode, subWarehouseCode, reserveKey,
                    new Gson().toJson(requestBody), responseBody,
                    "UNEXPECTED",
                    "Unexpected error calling UNLOAD_RESERVED_STOCK: " + ex.getMessage(),
                    ex, System.currentTimeMillis() - start
            ));
            throw ex;
        }
    }

    private ControlUnloadStockModel buildFailureModel(Long reference,
                                                      String userCreate,
                                                      String service,
                                                      String url,
                                                      String inventoryType,
                                                      String itemCode,
                                                      String warehouseCode,
                                                      String subWarehouseCode,
                                                      String reserveKey,
                                                      String requestJson,
                                                      String responseJson,
                                                      String errorCode,
                                                      String errorMessage,
                                                      Throwable ex,
                                                      long execMs) {

        ControlUnloadStockModel m = new ControlUnloadStockModel();
        m.setReference(reference);
        m.setUserCreate(userCreate);
        m.setService(service);
        m.setUrl(url);

        m.setInventoryType(inventoryType);
        m.setItemCode(itemCode);
        m.setWarehouseCode(warehouseCode);
        m.setSubWarehouseCode(subWarehouseCode);
        m.setReserveKey(reserveKey);

        m.setRequestJson(requestJson);
        m.setResponseJson(responseJson);

        m.setErrorCode(errorCode);
        m.setErrorMessage(errorMessage);

        m.setException(ex);
        m.setExecutionTimeMs(execMs);

        m.setStatus("PENDING");
        m.setCreated(LocalDateTime.now());
        return m;
    }

    private void persistFailure(ControlUnloadStockModel model) {
        try {
            controlUnloadStockService.saveControlUnloadStock(model);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
