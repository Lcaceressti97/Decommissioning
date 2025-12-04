package hn.com.tigo.selfconsumption.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.selfconsumption.exceptions.ExceptionHandler;
import hn.com.tigo.selfconsumption.models.ChangeCodeAutoconsumoModel;
import hn.com.tigo.selfconsumption.services.interfaces.IChangeCodeAutoconsumoService;
import hn.com.tigo.selfconsumption.utils.ResponseBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/charge-code-autoconsumo")
public class ChangeCodeAutoconsumoController {

	private final IChangeCodeAutoconsumoService changeCodeAutoconsumoService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public ChangeCodeAutoconsumoController(IChangeCodeAutoconsumoService changeCodeAutoconsumoService) {
		super();
		this.changeCodeAutoconsumoService = changeCodeAutoconsumoService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);

	}

	@GetMapping()
	public ResponseEntity<Object> getAllChangeCodeAutoconsumo(HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> changeCodeAutoconsumoService.getAllChangeCodeAutoconsumo(),
				request);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getChangeCodeAutoconsumoById(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler
				.handleExceptions(() -> this.changeCodeAutoconsumoService.getChangeCodeAutoconsumoById(id), request);

	}

	@PostMapping("/add")
	public ResponseEntity<Object> addChangeCodeAutoconsumo(@Valid @RequestBody ChangeCodeAutoconsumoModel model,
			HttpServletRequest httpRequest) {

		return exceptionHandler.handleExceptions(() -> {
			this.changeCodeAutoconsumoService.addChangeCodeAutoconsumo(model);
			return model;
		}, httpRequest);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updateChangeCodeAutoconsumo(@PathVariable Long id,
			@Valid @RequestBody ChangeCodeAutoconsumoModel model, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.changeCodeAutoconsumoService.updateChangeCodeAutoconsumo(id, model);
			return model;
		}, request);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteChangeCodeAutoconsumo(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.changeCodeAutoconsumoService.deleteChangeCodeAutoconsumo(id);
			return null;
		}, request);
	}
}
