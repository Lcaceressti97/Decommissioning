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
import hn.com.tigo.equipmentaccessoriesbilling.models.BranchOfficesModel;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IBranchOfficesService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.ResponseBuilder;

@RestController
@RequestMapping("/branchoffices")
public class BranchOfficesController {

	private final IBranchOfficesService branchOfficesService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public BranchOfficesController(IBranchOfficesService branchOfficesService) {
		super();
		this.branchOfficesService = branchOfficesService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);

	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAllBranchOfficesByPagination(Pageable pageable, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> branchOfficesService.getAllBranchOffices(pageable), request);

	}

	@GetMapping("/report")
	public ResponseEntity<Object> getAllBranchOfficesReport(@RequestParam String user, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> branchOfficesService.getBranchOfficesReport(user), request);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.branchOfficesService.getById(id), request);

	}

	@PostMapping("/add")
	public ResponseEntity<Object> add(@Valid @RequestBody BranchOfficesModel model, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.branchOfficesService.add(model);
			return model;
		}, request);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody BranchOfficesModel model,
			HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.branchOfficesService.update(id, model);
			return model;
		}, request);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.branchOfficesService.delete(id);
			return null;
		}, request);
	}
}
