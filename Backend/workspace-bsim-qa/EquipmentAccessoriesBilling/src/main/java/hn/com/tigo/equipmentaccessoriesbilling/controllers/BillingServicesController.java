package hn.com.tigo.equipmentaccessoriesbilling.controllers;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.equipmentaccessoriesbilling.exceptions.ExceptionHandler;
import hn.com.tigo.equipmentaccessoriesbilling.models.BillingServicesModel;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IBillingServicesService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.ResponseBuilder;

@RestController
@RequestMapping("/billingservices")
public class BillingServicesController {

	private final IBillingServicesService billingServicesService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public BillingServicesController(IBillingServicesService billingServicesService) {
		super();
		this.billingServicesService = billingServicesService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);

	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAllPageable(Pageable pageable, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> billingServicesService.getAllPageable(pageable), request);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.billingServicesService.getById(id), request);

	}

	@PostMapping("/add")
	public ResponseEntity<Object> add(@Valid @RequestBody BillingServicesModel model, HttpServletRequest httpRequest) {

		return exceptionHandler.handleExceptions(() -> {
			this.billingServicesService.add(model);
			return model;
		}, httpRequest);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody BillingServicesModel model,
			HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.billingServicesService.update(id, model);
			return model;
		}, request);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.billingServicesService.delete(id);
			return null;
		}, request);
	}
}
