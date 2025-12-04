package hn.com.tigo.equipmentinsurance.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import hn.com.tigo.equipmentinsurance.models.InsuranceClaimModel;
import hn.com.tigo.equipmentinsurance.services.interfaces.IInsuranceClaimService;
import hn.com.tigo.equipmentinsurance.utils.ExceptionHandler;
import hn.com.tigo.equipmentinsurance.utils.ResponseBuilder;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/insurance-claim")
public class InsuranceClaimController {

	private final IInsuranceClaimService insuranceClaimService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public InsuranceClaimController(IInsuranceClaimService insuranceClaimService) {
		this.insuranceClaimService = insuranceClaimService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);
	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAll(Pageable pageable, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> insuranceClaimService.getAll(pageable), request);

	}

	@GetMapping("/phone/{phone}")
	public ResponseEntity<Object> getByPhone(@PathVariable String phone, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.insuranceClaimService.getByPhone(phone), request);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.insuranceClaimService.getById(id), request);

	}

	@GetMapping("/esn/{actualEsn}")
	public ResponseEntity<Object> getByEsn(@PathVariable String actualEsn, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.insuranceClaimService.getByActualEsn(actualEsn), request);

	}

	@PostMapping("/add")
	public ResponseEntity<Object> add(@Valid @RequestBody InsuranceClaimModel model, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.insuranceClaimService.add(model);
			return model;
		}, request);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody InsuranceClaimModel model,
			HttpServletRequest request) {
		return exceptionHandler.handleExceptions(() -> {
			this.insuranceClaimService.update(id, model);
			return model;
		}, request);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.insuranceClaimService.delete(id);
			return null;
		}, request);
	}

}
