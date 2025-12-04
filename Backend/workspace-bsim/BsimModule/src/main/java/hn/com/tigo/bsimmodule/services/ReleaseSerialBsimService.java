package hn.com.tigo.bsimmodule.services;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import hn.com.tigo.bsimmodule.models.ConfigParametersModel;
import hn.com.tigo.bsimmodule.models.ReleaseSerialBsimModel;
import hn.com.tigo.bsimmodule.models.ReleaseSerialBsimRequest;
import hn.com.tigo.bsimmodule.services.interfaces.IConfigParametersService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.ws.rs.BadRequestException;


@Service
public class ReleaseSerialBsimService {

    private final IConfigParametersService configParametersService;

    public ReleaseSerialBsimService(IConfigParametersService configParametersService) {
        super();
        this.configParametersService = configParametersService;
    }

    public ReleaseSerialBsimModel releaseSeries(String accessToken, ReleaseSerialBsimRequest releaseSerialBsimRequest) {

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

        String reserveSeriesUrl = paramsAuthentication.get("RELEASE_SERIAL_ENDPOINT");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("inventory_type", releaseSerialBsimRequest.getInventoryType());
        requestBody.put("item_code", releaseSerialBsimRequest.getItemCode());
        requestBody.put("warehouse_code", releaseSerialBsimRequest.getWarehouseCode());
        requestBody.put("sub_warehouse_code", releaseSerialBsimRequest.getSubWarehouseCode());

        List<Map<String, String>> serialNumberList = releaseSerialBsimRequest.getSerialNumberList().stream().map(detail -> {
            Map<String, String> serialNumberMap = new HashMap<>();
            serialNumberMap.put("serial_number", detail.getSerialNumber());
            return serialNumberMap;
        }).collect(Collectors.toList());
        requestBody.put("serial_number_list", serialNumberList);

        HttpEntity<Object> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(reserveSeriesUrl, HttpMethod.POST, request,
                String.class);

        Gson gson = new Gson();
        Type reservationResponseType = new TypeToken<ReleaseSerialBsimModel>() {
        }.getType();
        ReleaseSerialBsimModel reserveSerialResponse = gson.fromJson(response.getBody(), reservationResponseType);

        if (reserveSerialResponse.getResult_code().endsWith("000")) {
            return reserveSerialResponse;
        } else {
            throw new BadRequestException("Error releasing series: " + reserveSerialResponse.getReservation_result_list().get(0).getReservation_result());
        }

    }
}
