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

import hn.com.tigo.equipmentinsurance.models.ReasonsModel;
import hn.com.tigo.equipmentinsurance.services.interfaces.IReasonsService;
import hn.com.tigo.equipmentinsurance.utils.ExceptionHandler;
import hn.com.tigo.equipmentinsurance.utils.ResponseBuilder;

@RestController
@RequestMapping("/reasons")
public class ReasonsController {

	private final IReasonsService reasonsService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public ReasonsController(IReasonsService reasonsService) {
		super();
		this.reasonsService = reasonsService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);
	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAllReasons(HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> reasonsService.getAllReasons(), request);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getReasonById(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.reasonsService.getReasonById(id), request);

	}

	@PostMapping("/add")
	public ResponseEntity<Object> addReason(@Valid @RequestBody ReasonsModel model, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.reasonsService.addReason(model);
			return model;
		}, request);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updateReason(@PathVariable Long id, @Valid @RequestBody ReasonsModel model,
			HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.reasonsService.updateReason(id, model);
			return model;
		}, request);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.reasonsService.deleteReason(id);
			return null;
		}, request);
	}

}
