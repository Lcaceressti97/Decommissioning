package hn.com.tigo.equipmentaccessoriesbilling.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.equipmentaccessoriesbilling.exceptions.ExceptionHandler;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IHistoricalDetailService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.ResponseBuilder;

@RestController
@RequestMapping("/historicaldetail")
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

	@GetMapping()
	public ResponseEntity<Object> getHistoricalByEsn(@RequestParam(value = "phone", required = false) String phone,
			HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.historicalDetailService.getHistoricalDetailByPhone(phone),
				request);

	}
}
