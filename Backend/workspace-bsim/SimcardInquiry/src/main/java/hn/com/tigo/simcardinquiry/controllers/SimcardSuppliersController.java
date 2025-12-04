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
import hn.com.tigo.simcardinquiry.models.SimcardSuppliersModel;
import hn.com.tigo.simcardinquiry.services.interfaces.ISimcardSuppliersService;
import hn.com.tigo.simcardinquiry.utils.ResponseBuilder;

@RestController
@RequestMapping("/simcardsuppliers")
public class SimcardSuppliersController {

	private final ISimcardSuppliersService simcardSuppliersService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public SimcardSuppliersController(ISimcardSuppliersService simcardSuppliersService) {
		super();
		this.simcardSuppliersService = simcardSuppliersService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);

	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAll(HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.simcardSuppliersService.getAll(), request);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.simcardSuppliersService.getById(id), request);

	}

	@PostMapping("/add")
	public ResponseEntity<Object> add(@Valid @RequestBody SimcardSuppliersModel model, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.simcardSuppliersService.createSupplier(model);
			return model;
		}, request);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody SimcardSuppliersModel model,
			HttpServletRequest request) {
		return exceptionHandler.handleExceptions(() -> {
			this.simcardSuppliersService.updateSupplier(id, model);
			return model;
		}, request);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.simcardSuppliersService.deleteSupplier(id);
			return null;
		}, request);
	}

}
