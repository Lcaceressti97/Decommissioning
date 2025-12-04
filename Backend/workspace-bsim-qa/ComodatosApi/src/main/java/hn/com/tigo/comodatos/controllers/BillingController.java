package hn.com.tigo.comodatos.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.comodatos.models.BillingModel;
import hn.com.tigo.comodatos.services.interfaces.IBillingService;
import hn.com.tigo.comodatos.utils.ResponseBuilder;

@RestController
@RequestMapping("/billing")
public class BillingController {

	private final IBillingService billingService;
	private final ResponseBuilder responseBuilder;

	public BillingController(IBillingService billingService) {
		super();
		this.billingService = billingService;
		this.responseBuilder = new ResponseBuilder();
	}

	@GetMapping("/invoicebynumberdei/{numberDei}")
	public ResponseEntity<Object> getBillingByNumberDei(@PathVariable String numberDei, HttpServletRequest request) {

		try {
			BillingModel model = this.billingService.getBillingByNumberDei(numberDei);
			return responseBuilder.buildSuccessResponse(request, model);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}
}
