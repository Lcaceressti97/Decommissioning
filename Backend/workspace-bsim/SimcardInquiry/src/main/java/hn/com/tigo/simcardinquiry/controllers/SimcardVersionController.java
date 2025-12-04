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
import hn.com.tigo.simcardinquiry.models.SimcardVersionModel;
import hn.com.tigo.simcardinquiry.services.interfaces.ISimcardVersionService;
import hn.com.tigo.simcardinquiry.utils.ResponseBuilder;

@RestController
@RequestMapping("/simcardversion")
public class SimcardVersionController {

	private final ISimcardVersionService simcardVersionService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public SimcardVersionController(ISimcardVersionService simcardVersionService) {
		super();
		this.simcardVersionService = simcardVersionService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);

	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAll(HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.simcardVersionService.getAll(), request);

	}

	@GetMapping("getversionbymodel/{idmodel}")
	public ResponseEntity<Object> getSimcardVersionByModel(@PathVariable Long idmodel, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.simcardVersionService.getVersionByIdModel(idmodel),
				request);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.simcardVersionService.getById(id), request);

	}

	@PostMapping("/add")
	public ResponseEntity<Object> add(@Valid @RequestBody SimcardVersionModel model, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.simcardVersionService.createVersion(model);
			return model;
		}, request);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody SimcardVersionModel model,
			HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.simcardVersionService.updateVersion(id, model);
			return model;
		}, request);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.simcardVersionService.deleteVersion(id);
			return null;
		}, request);
	}

}
