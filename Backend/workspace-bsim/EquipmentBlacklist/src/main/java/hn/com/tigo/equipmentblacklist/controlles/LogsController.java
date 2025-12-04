package hn.com.tigo.equipmentblacklist.controlles;

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

import hn.com.tigo.equipmentblacklist.exceptions.BadRequestException;
import hn.com.tigo.equipmentblacklist.models.GeneralError;
import hn.com.tigo.equipmentblacklist.models.GeneralResponse;
import hn.com.tigo.equipmentblacklist.models.LogsModel;
import hn.com.tigo.equipmentblacklist.services.interfaces.ILogsService;
import hn.com.tigo.equipmentblacklist.utils.ResponseBuilder;
import hn.com.tigo.equipmentblacklist.utils.Util;

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
			Page<LogsModel> models = logsService.getAllLogsByPagination(pageable);
			return responseBuilder.buildSuccessResponse(request, models);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@GetMapping("/daterange")
	public ResponseEntity<Object> getByDateRange(
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> endDate,
			HttpServletRequest request) {

		try {
			List<LogsModel> models = this.logsService.getLogsByDateRange(startDate, endDate);
			return responseBuilder.buildSuccessResponse(request, models);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@GetMapping("/searchbytypeerror")
	public ResponseEntity<Object> getLogsByTypeError(@RequestParam long typeError, HttpServletRequest request) {
		try {
			List<LogsModel> models = this.logsService.getLogsByTypeError(typeError);
			return responseBuilder.buildSuccessResponse(request, models);
		} catch (BadRequestException ex) {
			GeneralError error = new GeneralError();
			error.setCode("400");
			error.setUserMessage(ex.getMessage());
			error.setMoreInfo("No records found for the type error: " + typeError);
			error.setInternalMessage("check the log for more information");

			GeneralResponse gr = new Util().setErrorResponse(error);
			return ResponseEntity.badRequest().body(gr);
		}
	}
}
