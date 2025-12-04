package hn.com.tigo.equipmentinsurance.controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.equipmentinsurance.entities.InvoiceDetailEntity;
import hn.com.tigo.equipmentinsurance.models.BillingModel;
import hn.com.tigo.equipmentinsurance.services.interfaces.IBillingService;
import hn.com.tigo.equipmentinsurance.services.interfaces.IInvoiceDetailService;
import hn.com.tigo.equipmentinsurance.utils.ExceptionHandler;
import hn.com.tigo.equipmentinsurance.utils.ResponseBuilder;



@RestController
@RequestMapping("/billing")
public class BillingController {
	
	private final IBillingService billingService;
	private final IInvoiceDetailService invoiceDetailService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public BillingController(IBillingService billingService, IInvoiceDetailService invoiceDetailService) {
		super();
		this.billingService = billingService;
		this.responseBuilder = new ResponseBuilder();
		this.invoiceDetailService = invoiceDetailService;
		this.exceptionHandler = new ExceptionHandler(responseBuilder);

	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}
	
	@PostMapping("/insurance-claim")
	public ResponseEntity<Object> addInvoiceInsuranceClaim(@Valid @RequestBody BillingModel model, HttpServletRequest request) {
		return exceptionHandler.handleExceptions(() -> {
			Long generatedId = this.billingService.addInvoiceInsuranceClaim(model);

			BillingModel billingModel = this.billingService.getById(generatedId);

			List<InvoiceDetailEntity> product = this.invoiceDetailService.getDetailByIdInvoiceEntity(generatedId);

			billingModel.setInvoiceDetails(product);

			return billingModel;
		}, request);
	}

}
