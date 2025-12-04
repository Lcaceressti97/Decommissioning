package hn.com.tigo.bsimmodule.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.BadRequestException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import hn.com.tigo.bsimmodule.models.AuthenticationBsimResponse;
import hn.com.tigo.bsimmodule.models.ConfigParametersModel;
import hn.com.tigo.bsimmodule.services.interfaces.IConfigParametersService;

@Service
public class AuthenticationBsimService {

    private final IConfigParametersService configParametersService;

    public AuthenticationBsimService(IConfigParametersService configParametersService) {
        super();
        this.configParametersService = configParametersService;
    }

    public AuthenticationBsimResponse getAccessToken() {

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

        String endpoint = paramsAuthentication.get("ENDPOINT");
        String grantType = paramsAuthentication.get("GRANT_TYPE");
        String clientId = paramsAuthentication.get("CLIENT_ID");
        String clientSecret = paramsAuthentication.get("CLIENT_SECRET");
        String username = paramsAuthentication.get("USERNAME");
        String password = paramsAuthentication.get("PASSWORD");

        String authUrl = endpoint;
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", grantType);
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("username", username);
        formData.add("password", password);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        ResponseEntity<String> response = restTemplate.postForEntity(authUrl, formData, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {

            try {
                Gson gson = new Gson();
                AuthenticationBsimResponse authResponse = gson.fromJson(response.getBody(),
                        AuthenticationBsimResponse.class);
                return authResponse;
            } catch (Exception e) {
                throw new BadRequestException("Error getting authentication response", e);
            }
        } else {
            throw new BadRequestException("Error getting authentication token");
        }
    }
}