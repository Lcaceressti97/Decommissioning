package hn.com.tigo.equipmentinsurance.controllers;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

import hn.com.tigo.equipmentinsurance.models.InsuranceRatesModel;
import hn.com.tigo.equipmentinsurance.services.interfaces.IInsuranceRatesService;
import hn.com.tigo.equipmentinsurance.utils.ExceptionHandler;
import hn.com.tigo.equipmentinsurance.utils.ResponseBuilder;

@RestController
@RequestMapping("/insurance-rates")
public class InsuranceRatesController {

	private final IInsuranceRatesService insuranceRatesService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public InsuranceRatesController(IInsuranceRatesService insuranceRatesService) {
		super();
		this.insuranceRatesService = insuranceRatesService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);
	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAll(HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> insuranceRatesService.getAll(), request);

	}

	@GetMapping("/monthly-fees/{model}")
	public ResponseEntity<Object> getMonthlyFeesByModel(@PathVariable String model, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.insuranceRatesService.getMonthlyFeesByModel(model),
				request);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.insuranceRatesService.getById(id), request);

	}

	@PostMapping("/add")
	public ResponseEntity<Object> add(@Valid @RequestBody InsuranceRatesModel model, HttpServletRequest httpRequest) {

		return exceptionHandler.handleExceptions(() -> {
			this.insuranceRatesService.add(model);
			return model;
		}, httpRequest);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody InsuranceRatesModel model,
			HttpServletRequest httpRequest) {

		return exceptionHandler.handleExceptions(() -> {
			this.insuranceRatesService.update(id, model);
			return model;
		}, httpRequest);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest httpRequest) {

		return exceptionHandler.handleExceptions(() -> {
			this.insuranceRatesService.delete(id);
			return null;
		}, httpRequest);
	}

}
