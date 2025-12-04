package hn.com.tigo.simcardinquiry.controllers;

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

import hn.com.tigo.simcardinquiry.exceptions.ExceptionHandler;
import hn.com.tigo.simcardinquiry.models.SimcardCustomerModel;
import hn.com.tigo.simcardinquiry.services.interfaces.ISimcardCustomerService;
import hn.com.tigo.simcardinquiry.utils.ResponseBuilder;

@RestController
@RequestMapping("/simcardcustomer")
public class SimcardCustomerController {

	private final ISimcardCustomerService simcardCustomerService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public SimcardCustomerController(ISimcardCustomerService simcardCustomerService) {
		super();
		this.simcardCustomerService = simcardCustomerService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);

	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAll(HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.simcardCustomerService.getAll(), request);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.simcardCustomerService.getById(id), request);

	}

	@PostMapping("/add")
	public ResponseEntity<Object> add(@Valid @RequestBody SimcardCustomerModel model, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.simcardCustomerService.createCustomer(model);
			return model;
		}, request);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody SimcardCustomerModel model,
			HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.simcardCustomerService.updateCustomer(id, model);
			return model;
		}, request);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.simcardCustomerService.deleteCustomer(id);
			return null;
		}, request);
	}

}
