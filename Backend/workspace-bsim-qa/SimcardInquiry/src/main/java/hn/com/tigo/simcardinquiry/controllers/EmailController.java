package hn.com.tigo.simcardinquiry.controllers;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.simcardinquiry.exceptions.ExceptionHandler;
import hn.com.tigo.simcardinquiry.models.EmailServiceModel;
import hn.com.tigo.simcardinquiry.services.emailservice.GeneralResponse;
import hn.com.tigo.simcardinquiry.services.interfaces.IEmailService;
import hn.com.tigo.simcardinquiry.utils.ResponseBuilder;

@RestController
@RequestMapping("/simcardemail")
public class EmailController {

	private final IEmailService emailService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public EmailController(IEmailService emailService) {
		super();
		this.emailService = emailService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);

	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@PostMapping("/send")
	public ResponseEntity<Object> sendEmail(@RequestBody EmailServiceModel model, HttpServletRequest request) {
		return exceptionHandler.handleExceptions(() -> {
			GeneralResponse response = this.emailService.sendEmail(model);
			return response;
		}, request);
	}

}
