package hn.com.tigo.bsimmodule.controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import hn.com.tigo.bsimmodule.models.ConfigParametersModel;
import hn.com.tigo.bsimmodule.services.interfaces.IConfigParametersService;
import hn.com.tigo.bsimmodule.utils.ResponseBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/config-parameters")
public class ConfigParametersController {

    private final IConfigParametersService configParametersService;
    private final ResponseBuilder responseBuilder;

    public ConfigParametersController(IConfigParametersService configParametersService) {
        super();
        this.configParametersService = configParametersService;
        this.responseBuilder = new ResponseBuilder();
    }

    @PostConstruct
    void setGlobalSecurityContext() {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @GetMapping("/searchById")
    public ResponseEntity<Object> getByStatus(@RequestParam Long idApplication, HttpServletRequest request) {
        try {
            List<ConfigParametersModel> models = configParametersService.getByIdApplication(idApplication);
            return responseBuilder.buildSuccessResponse(request, models);
        } catch (Exception ex) {
            return responseBuilder.buildErrorResponse(ex, request);
        }
    }

}