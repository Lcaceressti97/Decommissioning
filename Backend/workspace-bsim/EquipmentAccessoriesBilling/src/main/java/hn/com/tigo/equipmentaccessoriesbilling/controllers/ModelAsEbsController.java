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
import hn.com.tigo.equipmentaccessoriesbilling.models.ModelAsEbsModel;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IModelAsEbsService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.ResponseBuilder;

@RestController
@RequestMapping("/model-as-ebs")
public class ModelAsEbsController {

	private final IModelAsEbsService modelAsEbsService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public ModelAsEbsController(IModelAsEbsService modelAsEbsService) {
		super();
		this.modelAsEbsService = modelAsEbsService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);

	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAll(Pageable pageable,HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> modelAsEbsService.getAllModelAsEbs(pageable), request);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.modelAsEbsService.getModelAsEbsById(id), request);

	}

	@GetMapping("/codmod/{codmod}")
	public ResponseEntity<Object> getByCodMod(@PathVariable String codmod, HttpServletRequest request) {

		return exceptionHandler.handleExceptionsEbsModel(() -> this.modelAsEbsService.findByCodMod(codmod), request);

	}

	@GetMapping("/cod-ebs/{codEbs}")
	public ResponseEntity<Object> getModelAsEbsByCodEbs(@PathVariable String codEbs, HttpServletRequest request) {

		return exceptionHandler.handleExceptionsEbsModel(() -> this.modelAsEbsService.getModelAsEbsByCodEbs(codEbs), request);

	}

	@PostMapping("/add")
	public ResponseEntity<Object> add(@Valid @RequestBody ModelAsEbsModel model, HttpServletRequest httpRequest) {

		return exceptionHandler.handleExceptions(() -> {
			this.modelAsEbsService.addModelAsEbs(model);
			return model;
		}, httpRequest);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody ModelAsEbsModel model,
			HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.modelAsEbsService.updateModelAsEbs(id, model);
			return model;
		}, request);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.modelAsEbsService.deleteModelAsEbs(id);
			return null;
		}, request);
	}
}
