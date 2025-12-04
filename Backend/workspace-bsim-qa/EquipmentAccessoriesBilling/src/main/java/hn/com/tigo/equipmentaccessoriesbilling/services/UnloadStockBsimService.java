package hn.com.tigo.equipmentaccessoriesbilling.services;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import hn.com.tigo.equipmentaccessoriesbilling.entities.ChannelEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.LogsServiceModel;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.ILogsServicesService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import hn.com.tigo.equipmentaccessoriesbilling.entities.InvoiceDetailEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.ConfigParametersModel;
import hn.com.tigo.equipmentaccessoriesbilling.models.UnloadStockBsimModel;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IConfigParametersService;

@Service
public class UnloadStockBsimService {

    private final IConfigParametersService configParametersService;
    private final ILogsServicesService logsServicesService;

    public UnloadStockBsimService(IConfigParametersService configParametersService, ILogsServicesService logsServicesService) {
        super();
        this.configParametersService = configParametersService;
        this.logsServicesService = logsServicesService;
    }

    public UnloadStockBsimModel unloadStock(String accessToken, String inventoryType, String itemCode,
                                            String warehouseCode, String subWarehouseCode, List<InvoiceDetailEntity> invoiceDetails, Long reference, String userCreate, ChannelEntity channelEntity) {

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
}
