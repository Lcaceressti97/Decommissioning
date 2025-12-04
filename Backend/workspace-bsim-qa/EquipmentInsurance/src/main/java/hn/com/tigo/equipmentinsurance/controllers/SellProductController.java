package hn.com.tigo.equipmentinsurance.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.equipmentinsurance.services.interfaces.ISellProductService;
import hn.com.tigo.equipmentinsurance.utils.ExceptionHandler;
import hn.com.tigo.equipmentinsurance.utils.ResponseBuilder;

@RestController
@RequestMapping("/sell-product")
public class SellProductController {

	private final ISellProductService sellProductService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public SellProductController(ISellProductService sellProductService) {
		this.sellProductService = sellProductService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);
	}

	@GetMapping("/billing-account/{billingAccount}")
	public ResponseEntity<Object> getSellProductByBillingAccount(@PathVariable String billingAccount,
			HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.sellProductService.executeTask(billingAccount), request);
	}
}
