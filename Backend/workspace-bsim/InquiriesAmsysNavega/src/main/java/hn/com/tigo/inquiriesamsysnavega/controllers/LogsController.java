package hn.com.tigo.inquiriesamsysnavega.controllers;

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

import hn.com.tigo.inquiriesamsysnavega.exceptions.BadRequestException;
import hn.com.tigo.inquiriesamsysnavega.models.GeneralError;
import hn.com.tigo.inquiriesamsysnavega.models.GeneralResponse;
import hn.com.tigo.inquiriesamsysnavega.models.LogsModel;
import hn.com.tigo.inquiriesamsysnavega.services.interfaces.ILogsService;
import hn.com.tigo.inquiriesamsysnavega.utils.Util;

@RestController
@RequestMapping("/logs")
public class LogsController {

	private final ILogsService logsService;
	private final Util util;

	public LogsController(ILogsService logsService) {
		super();
		this.logsService = logsService;
		this.util = new Util();
	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAllLogsByPagination(Pageable pageable, HttpServletRequest request) {

		Page<LogsModel> logsPage = logsService.getAllLogsByPagination(pageable);
		ResponseEntity<Object> responseEntity = ResponseEntity.ok(logsPage);
		Util.captureAndSetResponse(request, responseEntity);

		return responseEntity;
	}

	@GetMapping("/daterange")
	public ResponseEntity<Object> getByDateRange(
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> endDate,
			HttpServletRequest request) {

		List<LogsModel> models = this.logsService.getLogsByDateRange(startDate, endDate);
		ResponseEntity<Object> responseEntity = ResponseEntity.ok(util.setSuccessResponse(models));
		Util.captureAndSetResponse(request, responseEntity);

		return responseEntity;
	}

	@GetMapping("/searchbytypeerror")
	public ResponseEntity<Object> getLogsByTypeError(@RequestParam long typeError, HttpServletRequest request) {
		try {

			List<LogsModel> models = logsService.getLogsByTypeError(typeError);
			ResponseEntity<Object> responseEntity = ResponseEntity.ok(util.setSuccessResponse(models));
			Util.captureAndSetResponse(request, responseEntity);

			return responseEntity;

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
