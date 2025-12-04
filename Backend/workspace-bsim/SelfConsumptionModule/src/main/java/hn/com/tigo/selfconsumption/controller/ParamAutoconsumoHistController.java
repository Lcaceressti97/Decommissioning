package hn.com.tigo.selfconsumption.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.selfconsumption.exceptions.ExceptionHandler;
import hn.com.tigo.selfconsumption.models.ParamAutoconsumoHistModel;
import hn.com.tigo.selfconsumption.services.interfaces.IParamAutoconsumoHistService;
import hn.com.tigo.selfconsumption.utils.ResponseBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/parameter-autoconsumo-hist")
public class ParamAutoconsumoHistController {

	private final IParamAutoconsumoHistService paramAutoconsumoHistService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public ParamAutoconsumoHistController(IParamAutoconsumoHistService paramAutoconsumoHistService) {
		super();
		this.paramAutoconsumoHistService = paramAutoconsumoHistService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);

	}

	@GetMapping("/{idParameter}")
	public ResponseEntity<Object> getAllParamAutoconsumoHist(@PathVariable Long idParameter, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.paramAutoconsumoHistService.getAllParamAutoconsumoHist(idParameter),
				request);

	}

	@PostMapping("/add")
	public ResponseEntity<Object> addParamAutoconsumoHist(@Valid @RequestBody ParamAutoconsumoHistModel model,
			HttpServletRequest httpRequest) {

		return exceptionHandler.handleExceptions(() -> {
			this.paramAutoconsumoHistService.addParamAutoconsumoHist(model);
			return model;
		}, httpRequest);
	}

}
