package hn.com.tigo.simcardinquiry.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.simcardinquiry.exceptions.BadRequestException;
import hn.com.tigo.simcardinquiry.models.GeneralError;
import hn.com.tigo.simcardinquiry.models.GeneralResponse;
import hn.com.tigo.simcardinquiry.models.LogsSimcardModel;
import hn.com.tigo.simcardinquiry.services.interfaces.ILogsSimcardService;
import hn.com.tigo.simcardinquiry.utils.Util;

@RestController
@RequestMapping("/logsimcard")
public class LogsSimcardController {

	private final ILogsSimcardService logsService;
	private final Util util;

	public LogsSimcardController(ILogsSimcardService logsService) {
		super();
		this.logsService = logsService;
		this.util = new Util();
	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public Page<LogsSimcardModel> getAllLogsByPagination(Pageable pageable) {
		return logsService.getAllLogsByPagination(pageable);
	}

	@GetMapping("/daterange")
	public ResponseEntity<Object> getByDateRange(
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> endDate) {

		List<LogsSimcardModel> models = this.logsService.getLogsByDateRange(startDate, endDate);
		return ResponseEntity.ok(this.util.setSuccessResponse(models));
	}

	@GetMapping("/searchbytypeerror")
	public ResponseEntity<Object> getLogsByTypeError(@RequestParam long typeError) {
		try {
			List<LogsSimcardModel> models = this.logsService.getLogsByTypeError(typeError);
			return ResponseEntity.ok(util.setSuccessResponse(models));
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
