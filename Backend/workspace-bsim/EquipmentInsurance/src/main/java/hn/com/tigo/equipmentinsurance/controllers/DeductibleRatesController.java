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

import hn.com.tigo.equipmentinsurance.models.CalculateDeductibleRequest;
import hn.com.tigo.equipmentinsurance.models.DeductibleRatesModel;
import hn.com.tigo.equipmentinsurance.services.interfaces.IDeductibleRatesService;
import hn.com.tigo.equipmentinsurance.utils.ExceptionHandler;
import hn.com.tigo.equipmentinsurance.utils.ResponseBuilder;

@RestController
@RequestMapping("/deductible-rates")
public class DeductibleRatesController {

	private final IDeductibleRatesService deductibleRatesService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public DeductibleRatesController(IDeductibleRatesService deductibleRatesService) {
		super();
		this.deductibleRatesService = deductibleRatesService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);
	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAll(HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> deductibleRatesService.getAllDeductibleRates(), request);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.deductibleRatesService.getDeductibleRatesById(id), request);

	}

	@GetMapping("/model")
	public ResponseEntity<Object> getByUserCreate(@RequestParam String model, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> deductibleRatesService.getDeductibleRatesByModel(model),
				request);

	}

	@PostMapping("/calculate-deductible")
	public ResponseEntity<Object> getCalculateDeductible(@Valid @RequestBody CalculateDeductibleRequest model,
			HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> deductibleRatesService.getCalculateDeductible(model),
				request);
	}

	@PostMapping("/add")
	public ResponseEntity<Object> add(@Valid @RequestBody DeductibleRatesModel model, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.deductibleRatesService.addDeductibleRates(model);
			return model;
		}, request);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody DeductibleRatesModel model,
			HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.deductibleRatesService.updateDeductibleRates(id, model);
			return model;
		}, request);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.deductibleRatesService.deleteDeductibleRates(id);
			return null;
		}, request);
	}

}
