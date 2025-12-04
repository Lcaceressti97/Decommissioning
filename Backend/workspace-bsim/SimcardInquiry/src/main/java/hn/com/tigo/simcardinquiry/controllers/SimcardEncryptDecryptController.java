package hn.com.tigo.simcardinquiry.controllers;

import hn.com.tigo.simcardinquiry.exceptions.ExceptionHandler;
import hn.com.tigo.simcardinquiry.models.SimcardEncryptDecryptRequest;
import hn.com.tigo.simcardinquiry.models.SimcardEncryptDecryptResponse;
import hn.com.tigo.simcardinquiry.services.interfaces.ISimcardEncryptDecryptService;
import hn.com.tigo.simcardinquiry.utils.ResponseBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class SimcardEncryptDecryptController {

    private final ISimcardEncryptDecryptService _simcardEncryptService;
    private final ResponseBuilder responseBuilder;
    private final ExceptionHandler exceptionHandler;


    public SimcardEncryptDecryptController(ISimcardEncryptDecryptService simcardEncryptService) {
        _simcardEncryptService = simcardEncryptService;
        this.responseBuilder = new ResponseBuilder();
        this.exceptionHandler = new ExceptionHandler(responseBuilder);
    }

    @PostConstruct
    void setGlobalSecurityContext() {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @PostMapping("encrypt")
    public ResponseEntity<Object> simcardEncrypt(@Valid @RequestBody List<SimcardEncryptDecryptRequest> simcardRequests, HttpServletRequest request) {
        return exceptionHandler.handleExceptions(() -> {
            List<SimcardEncryptDecryptResponse> responses = new ArrayList<>();
            for (SimcardEncryptDecryptRequest simcardRequest : simcardRequests) {
                responses.add(this._simcardEncryptService.simcardEncrypt(simcardRequest));
            }
            return responses;
        }, request);
    }

    @PostMapping("decrypt")
    public ResponseEntity<Object> simcardDecrypt(@Valid @RequestBody List<SimcardEncryptDecryptRequest> simcardRequests, HttpServletRequest request) {
        return exceptionHandler.handleExceptions(() -> {
            List<SimcardEncryptDecryptResponse> responses = new ArrayList<>();
            for (SimcardEncryptDecryptRequest simcardRequest : simcardRequests) {
                responses.add(this._simcardEncryptService.simcardDecrypt(simcardRequest));
            }
            return responses;
        }, request);
    }
}
