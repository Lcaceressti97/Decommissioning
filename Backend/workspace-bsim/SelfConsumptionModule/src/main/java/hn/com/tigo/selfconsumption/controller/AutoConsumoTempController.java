package hn.com.tigo.selfconsumption.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.selfconsumption.exceptions.ExceptionHandler;
import hn.com.tigo.selfconsumption.services.interfaces.IAutoConsumoTempService;
import hn.com.tigo.selfconsumption.utils.ResponseBuilder;

import java.time.LocalDate;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/autoconsumo-temp")
public class AutoConsumoTempController {

	private final IAutoConsumoTempService autoConsumoTempService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public AutoConsumoTempController(IAutoConsumoTempService autoConsumoTempService) {
		super();
		this.autoConsumoTempService = autoConsumoTempService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);

	}

	@GetMapping()
	public ResponseEntity<Object> getAllAutoConsumoTemp(HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> autoConsumoTempService.getAllAutoConsumoTemp(), request);

	}

	@GetMapping("/findbyfilter/{value}/{type}")
	public ResponseEntity<Object> findAutoConsumoTempByFilter(@PathVariable String value, @PathVariable int type,
			HttpServletRequest request) {

		return exceptionHandler
				.handleExceptions(() -> this.autoConsumoTempService.findAutoConsumoTempByFilter(type, value), request);

	}

	@GetMapping("/daterange")
	public ResponseEntity<Object> getAutoConsumoTempByDateRange(
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> endDate,
			HttpServletRequest request) {

		return exceptionHandler.handleExceptions(
				() -> this.autoConsumoTempService.getAutoConsumoTempByDateRange(startDate, endDate), request);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getAutoConsumoTempById(@PathVariable String id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.autoConsumoTempService.getAutoConsumoTempById(id), request);

	}

}
