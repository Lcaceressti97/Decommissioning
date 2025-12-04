package hn.com.tigo.equipmentaccessoriesbilling.controllers;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.equipmentaccessoriesbilling.exceptions.ExceptionHandler;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IGenerationReportInvService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.ResponseBuilder;

@RestController
@RequestMapping("/generationreportinv")
public class GenerationReportInvController {

	private final IGenerationReportInvService generationReportInvService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public GenerationReportInvController(IGenerationReportInvService generationReportInvService) {
		this.generationReportInvService = generationReportInvService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);
	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAll(Pageable pageable, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> generationReportInvService.getAll(pageable), request);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.generationReportInvService.getById(id), request);

	}

	@PostMapping("/generate")
	public ResponseEntity<Object> generateReport(HttpServletRequest request) {
		return exceptionHandler.handleExceptions(() -> {
			return generationReportInvService.generateReport();
		}, request);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.generationReportInvService.delete(id);
			return null;
		}, request);
	}

}
