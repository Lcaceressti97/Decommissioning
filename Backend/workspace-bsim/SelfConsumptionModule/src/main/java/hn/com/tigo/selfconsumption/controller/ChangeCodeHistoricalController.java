package hn.com.tigo.selfconsumption.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.selfconsumption.exceptions.ExceptionHandler;
import hn.com.tigo.selfconsumption.models.ChangeCodeHistoricalModel;
import hn.com.tigo.selfconsumption.services.interfaces.IChangeCodeHistoricalService;
import hn.com.tigo.selfconsumption.utils.ResponseBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/charge-code-historical")
public class ChangeCodeHistoricalController {

	private final IChangeCodeHistoricalService changeCodeHistoricalService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public ChangeCodeHistoricalController(IChangeCodeHistoricalService changeCodeHistoricalService) {
		super();
		this.changeCodeHistoricalService = changeCodeHistoricalService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);

	}

	@GetMapping("/{chargeCodeId}")
	public ResponseEntity<Object> getChangeCodeHistoricalById(@PathVariable Long chargeCodeId,
			HttpServletRequest request) {

		return exceptionHandler
				.handleExceptions(() -> changeCodeHistoricalService.getChangeCodeHistoricalById(chargeCodeId), request);

	}

	@PostMapping("/add")
	public ResponseEntity<Object> addChangeCodeHistorical(@Valid @RequestBody ChangeCodeHistoricalModel model,
			HttpServletRequest httpRequest) {

		return exceptionHandler.handleExceptions(() -> {
			this.changeCodeHistoricalService.addChangeCodeHistorical(model);
			return model;
		}, httpRequest);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updateChangeCodeHistorical(@PathVariable Long id,
			@Valid @RequestBody ChangeCodeHistoricalModel model, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.changeCodeHistoricalService.updateChangeCodeHistorical(id, model);
			return model;
		}, request);
	}
}
