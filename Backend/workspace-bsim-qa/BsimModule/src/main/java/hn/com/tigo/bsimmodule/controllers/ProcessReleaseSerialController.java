package hn.com.tigo.bsimmodule.controllers;

import hn.com.tigo.bsimmodule.exceptions.ExceptionHandler;
import hn.com.tigo.bsimmodule.models.ReleaseSerialBsimRequest;
import hn.com.tigo.bsimmodule.services.ProcessReleaseSerialService;
import hn.com.tigo.bsimmodule.utils.ResponseBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/release-serial")
public class ProcessReleaseSerialController {

    private final ProcessReleaseSerialService processReleaseSerialService;
    private final ResponseBuilder responseBuilder;
    private final ExceptionHandler exceptionHandler;

    public ProcessReleaseSerialController(ProcessReleaseSerialService processReleaseSerialService) {
        super();
        this.processReleaseSerialService = processReleaseSerialService;
        this.responseBuilder = new ResponseBuilder();
        this.exceptionHandler = new ExceptionHandler(responseBuilder);

    }

    @PostConstruct
    void setGlobalSecurityContext() {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @PostMapping("/single")
    public ResponseEntity<Object> releaseSingleSeries(@RequestBody ReleaseSerialBsimRequest request, HttpServletRequest httpRequest) {
        return exceptionHandler.handleExceptions(() -> {
            this.processReleaseSerialService.releaseSingleSeries(request);
            return request;
        }, httpRequest);
    }

    @PostMapping("/massive")
    public ResponseEntity<Object> releaseSeriesFromFile(@RequestParam("file") MultipartFile file, @RequestParam String releaseType, HttpServletRequest httpRequest) {
        return exceptionHandler.handleExceptions(() -> {
            Long logId = this.processReleaseSerialService.releaseSeriesFromFile(file, releaseType);
            return ResponseEntity.ok().body("Log ID: " + logId);
        }, httpRequest);
    }
}
