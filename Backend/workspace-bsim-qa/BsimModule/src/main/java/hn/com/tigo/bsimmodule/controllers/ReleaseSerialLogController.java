package hn.com.tigo.bsimmodule.controllers;

import hn.com.tigo.bsimmodule.exceptions.ExceptionHandler;
import hn.com.tigo.bsimmodule.services.interfaces.IReleaseSerialLogService;
import hn.com.tigo.bsimmodule.utils.ResponseBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/release-serial-log")
public class ReleaseSerialLogController {

    private final IReleaseSerialLogService releaseSerialLogService;
    private final ExceptionHandler exceptionHandler;

    public ReleaseSerialLogController(IReleaseSerialLogService releaseSerialLogService) {
        super();
        this.releaseSerialLogService = releaseSerialLogService;
        ResponseBuilder responseBuilder = new ResponseBuilder();
        this.exceptionHandler = new ExceptionHandler(responseBuilder);
    }

    @PostConstruct
    void setGlobalSecurityContext() {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @GetMapping()
    public ResponseEntity<Object> getAll(Pageable pageable, HttpServletRequest request) {
        return exceptionHandler.handleExceptions(() -> releaseSerialLogService.getAllReleaseSerialLog(pageable), request);

    }

    @GetMapping("/by-serial/{serialNumber}")
    public ResponseEntity<Object> getLogBySerialNumber(
            @PathVariable String serialNumber,
            HttpServletRequest request) {
        return exceptionHandler.handleExceptions(() -> releaseSerialLogService.getLogBySerialNumber(serialNumber), request);
    }
}
