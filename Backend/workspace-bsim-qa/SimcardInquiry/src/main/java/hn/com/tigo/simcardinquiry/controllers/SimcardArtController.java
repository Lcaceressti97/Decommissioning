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
import hn.com.tigo.simcardinquiry.models.SimcardArtModel;
import hn.com.tigo.simcardinquiry.services.interfaces.ISimcardArtService;
import hn.com.tigo.simcardinquiry.utils.ResponseBuilder;

@RestController
@RequestMapping("/simcardart")
public class SimcardArtController {

	private final ISimcardArtService simcardArtService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public SimcardArtController(ISimcardArtService simcardArtService) {
		super();
		this.simcardArtService = simcardArtService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);

	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAll(HttpServletRequest request) {
		return exceptionHandler.handleExceptions(() -> this.simcardArtService.getAll(), request);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable Long id, HttpServletRequest request) {
		return exceptionHandler.handleExceptions(() -> this.simcardArtService.getById(id), request);
	}

	@PostMapping("/add")
	public ResponseEntity<Object> add(@Valid @RequestBody SimcardArtModel model, HttpServletRequest request) {
		return exceptionHandler.handleExceptions(() -> {
			this.simcardArtService.createArt(model);
			return model;
		}, request);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody SimcardArtModel model,
			HttpServletRequest request) {
		return exceptionHandler.handleExceptions(() -> {
			this.simcardArtService.updateArt(id, model);
			return model;
		}, request);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {
		return exceptionHandler.handleExceptions(() -> {
			this.simcardArtService.deleteArt(id);
			return null;
		}, request);
	}
}
