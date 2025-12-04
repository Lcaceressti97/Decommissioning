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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.equipmentinsurance.models.PremiumRatesModel;
import hn.com.tigo.equipmentinsurance.services.interfaces.IPremiumRatesServices;
import hn.com.tigo.equipmentinsurance.utils.ExceptionHandler;
import hn.com.tigo.equipmentinsurance.utils.ResponseBuilder;

@RestController
@RequestMapping("/premiumrates")
public class PremiumRatesController {

	private final IPremiumRatesServices premiumRatesServices;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public PremiumRatesController(IPremiumRatesServices premiumRatesServices) {
		super();
		this.premiumRatesServices = premiumRatesServices;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);
	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAll(HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> premiumRatesServices.getAll(), request);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.premiumRatesServices.getPremiumRatesById(id), request);

	}

	@GetMapping("/model")
	public ResponseEntity<Object> getByModel(@RequestParam String model, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> premiumRatesServices.getPremiumRatesByModel(model), request);

	}

	@PostMapping("/add")
	public ResponseEntity<Object> add(@Valid @RequestBody PremiumRatesModel model, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.premiumRatesServices.addPremiumRates(model);
			return model;
		}, request);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody PremiumRatesModel model,
			HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.premiumRatesServices.updatePremiumRates(id, model);
			return model;
		}, request);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.premiumRatesServices.deletePremiumRates(id);
			return null;
		}, request);
	}

}
