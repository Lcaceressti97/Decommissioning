package hn.com.tigo.selfconsumption.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.selfconsumption.exceptions.ExceptionHandler;
import hn.com.tigo.selfconsumption.models.ParametersAutoconsumoModel;
import hn.com.tigo.selfconsumption.services.interfaces.IParametersAutoconsumoService;
import hn.com.tigo.selfconsumption.utils.ResponseBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/parameter-autoconsumo")
public class ParametersAutoconsumoController {

	private final IParametersAutoconsumoService parametersAutoconsumoService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public ParametersAutoconsumoController(IParametersAutoconsumoService parametersAutoconsumoService) {
		super();
		this.parametersAutoconsumoService = parametersAutoconsumoService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);

	}

	@GetMapping()
	public ResponseEntity<Object> getAllParametersAutoconsumo(HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> parametersAutoconsumoService.getAllParametersAutoconsumo(),
				request);

	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updateParametersAutoconsumo(@PathVariable Long id,
			@Valid @RequestBody ParametersAutoconsumoModel model, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.parametersAutoconsumoService.updateParametersAutoconsumo(id, model);
			return model;
		}, request);
	}
}
