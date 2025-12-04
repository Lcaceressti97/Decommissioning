package hn.com.tigo.equipmentaccessoriesbilling.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.equipmentaccessoriesbilling.models.LogsModel;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.ILogsService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.ResponseBuilder;

@RestController
@RequestMapping("/logs")
public class LogsController {

    private final ILogsService logsService;
    private final ResponseBuilder responseBuilder;

    public LogsController(ILogsService logsService) {
        super();
        this.logsService = logsService;
        this.responseBuilder = new ResponseBuilder();
    }

    @PostConstruct
    void setGlobalSecurityContext() {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @GetMapping()
    public ResponseEntity<Object> getAllLogsByPagination(Pageable pageable, HttpServletRequest request) {
        try {
            Page<LogsModel> getAllLogsByPagination = logsService.getAllLogsByPagination(pageable);
            return responseBuilder.buildSuccessResponse(request, getAllLogsByPagination);

        } catch (Exception ex) {
            return responseBuilder.buildErrorResponse(ex, request);
        }
    }

    @GetMapping("/daterange")
    public ResponseEntity<Object> getByDateRange(Pageable pageable,
                                                 @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> startDate,
                                                 @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> endDate,
                                                 HttpServletRequest request) {

        try {
            Page<LogsModel> models = this.logsService.getLogsByDateRange(pageable, startDate, endDate);
            return responseBuilder.buildSuccessResponse(request, models);

        } catch (Exception ex) {
            return responseBuilder.buildErrorResponse(ex, request);
        }
    }

    @GetMapping("/searchbytypeerror")
    public ResponseEntity<Object> getLogsByTypeError(Pageable pageable, @RequestParam long typeError, HttpServletRequest request) {
        try {
            Page<LogsModel> models = this.logsService.getLogsByTypeError(pageable, typeError);
            return responseBuilder.buildSuccessResponse(request, models);
        } catch (Exception ex) {
            return responseBuilder.buildErrorResponse(ex, request);
        }
    }
}
