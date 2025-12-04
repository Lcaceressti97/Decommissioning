package hn.com.tigo.simcardinquiry.controllers;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.simcardinquiry.exceptions.ExceptionHandler;
import hn.com.tigo.simcardinquiry.services.interfaces.IConfigParameterService;
import hn.com.tigo.simcardinquiry.utils.ResponseBuilder;

@RestController
@RequestMapping("/parameters")
public class ConfigParameterController {

	private final IConfigParameterService configParameterService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public ConfigParameterController(IConfigParameterService configParameterService) {
		super();
		this.configParameterService = configParameterService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);

	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping("/parametername/{parameterName}")
	public ResponseEntity<Object> getById(@PathVariable String parameterName, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.configParameterService.findByParameterName(parameterName),
				request);

	}
}
