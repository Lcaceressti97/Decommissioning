package hn.com.tigo.equipmentaccessoriesbilling.controllers;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.equipmentaccessoriesbilling.models.ExemptInvoiceApprovalsModel;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IExemptInvoiceApprovalsService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.Util;

@RestController
@RequestMapping("/exemptinvoiceapprovals")
public class ExemptInvoiceApprovalsController {

	private final IExemptInvoiceApprovalsService exemptInvoiceApprovalsService;
	private final Util util;

	public ExemptInvoiceApprovalsController(IExemptInvoiceApprovalsService exemptInvoiceApprovalsService) {
		this.exemptInvoiceApprovalsService = exemptInvoiceApprovalsService;
		this.util = new Util();
	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@PostMapping("/add")
	public ResponseEntity<Object> add(@Valid @RequestBody ExemptInvoiceApprovalsModel model) {
		this.exemptInvoiceApprovalsService.add(model);
		return ResponseEntity.ok(this.util.setSuccessWithoutData());
	}
}
