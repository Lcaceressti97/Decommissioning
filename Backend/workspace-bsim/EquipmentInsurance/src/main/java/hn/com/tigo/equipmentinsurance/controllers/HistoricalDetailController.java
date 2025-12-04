package hn.com.tigo.equipmentinsurance.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.equipmentinsurance.services.interfaces.IHistoricalDetailService;
import hn.com.tigo.equipmentinsurance.utils.ExceptionHandler;
import hn.com.tigo.equipmentinsurance.utils.ResponseBuilder;

@RestController
@RequestMapping("/historical-detail")
public class HistoricalDetailController {

	private final IHistoricalDetailService historicalDetailService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public HistoricalDetailController(IHistoricalDetailService historicalDetailService) {
		super();
		this.historicalDetailService = historicalDetailService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);
	}

	@GetMapping("/details")
	public ResponseEntity<Object> getHistoricalByEsn(@RequestParam(value = "esnImei", required = false) String esnImei,
			HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> historicalDetailService.getHistoricalByEsnImei(esnImei),
				request);

	}
}
