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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.equipmentaccessoriesbilling.exceptions.ExceptionHandler;
import hn.com.tigo.equipmentaccessoriesbilling.models.ControlUserPermissionsModel;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IControlUserPermissionsService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.ResponseBuilder;

@RestController
@RequestMapping("/controluserpermissions")
public class ControlUserPermissionsController {

	private final IControlUserPermissionsService controlUserPermissionsService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public ControlUserPermissionsController(IControlUserPermissionsService controlUserPermissionsService) {
		super();
		this.controlUserPermissionsService = controlUserPermissionsService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);

	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAll(Pageable pageable, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> controlUserPermissionsService.getAll(pageable), request);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.controlUserPermissionsService.getById(id), request);

	}

	@GetMapping("/searchbyusername")
	public ResponseEntity<Object> getByUserCreate(@RequestParam String username, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> controlUserPermissionsService.getByUserName(username), request);

	}

	@GetMapping("/issuingusers")
	public ResponseEntity<Object> getIssuingUsers(HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.controlUserPermissionsService.getIssuingUsers(), request);

	}

	@PostMapping("/add")
	public ResponseEntity<Object> add(@Valid @RequestBody ControlUserPermissionsModel model,
			HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.controlUserPermissionsService.add(model);
			return model;
		}, request);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody ControlUserPermissionsModel model,
			HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.controlUserPermissionsService.update(id, model);
			return model;
		}, request);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.controlUserPermissionsService.delete(id);
			return null;
		}, request);
	}

}
