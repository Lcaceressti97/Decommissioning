package hn.com.tigo.selfconsumption.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.selfconsumption.exceptions.ExceptionHandler;
import hn.com.tigo.selfconsumption.services.interfaces.IOfferingInfoService;
import hn.com.tigo.selfconsumption.utils.ResponseBuilder;

@RestController
@RequestMapping("/offering-info")
public class OfferingInfoController {

	private final IOfferingInfoService offeringInfoService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public OfferingInfoController(IOfferingInfoService offeringInfoService) {
		super();
		this.offeringInfoService = offeringInfoService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);

	}
	
	@GetMapping("/{offeringId}")
	public ResponseEntity<Object> getOfferingInfo(@PathVariable String offeringId, HttpServletRequest request) {

		return exceptionHandler
				.handleExceptions(() -> this.offeringInfoService.executeTask(offeringId), request);

	}

}
