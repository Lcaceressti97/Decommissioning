package hn.com.tigo.simcardinquiry.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.simcardinquiry.exceptions.ExceptionHandler;
import hn.com.tigo.simcardinquiry.models.SimcardInquiryRequest;
import hn.com.tigo.simcardinquiry.models.SimcardInquiryResponse;
import hn.com.tigo.simcardinquiry.services.interfaces.ISimcardInquiryService;
import hn.com.tigo.simcardinquiry.utils.ResponseBuilder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class SimcardInquiryController {

	private final ISimcardInquiryService simcardInquiryService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public SimcardInquiryController(ISimcardInquiryService simcardInquiryService) {
		super();
		this.simcardInquiryService = simcardInquiryService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);

	}

	@PostMapping("/simcardinfo/simcardinquiry")
	public ResponseEntity<?> inquireSimcard(@RequestBody SimcardInquiryRequest request,
			HttpServletRequest httpRequest) {
		return exceptionHandler.handleControllerExceptions(() -> {
			SimcardInquiryResponse response = simcardInquiryService.processSimcardInquiry(request);
			return responseBuilder.buildSuccessResponseInquireSimcard(httpRequest, response);
		}, httpRequest);
	}

	@PostMapping("/simcardinfo/imsiinquiry")
	public ResponseEntity<?> inquireImsi(@RequestBody SimcardInquiryRequest request, HttpServletRequest httpRequest) {
		return exceptionHandler.handleControllerExceptions(() -> {
			SimcardInquiryResponse response = simcardInquiryService.processImsiInquiry(request);
			return responseBuilder.buildSuccessResponseInquireSimcard(httpRequest, response);
		}, httpRequest);
	}

}
