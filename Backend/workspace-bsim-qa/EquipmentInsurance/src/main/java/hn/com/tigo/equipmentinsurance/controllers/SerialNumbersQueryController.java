package hn.com.tigo.equipmentinsurance.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.equipmentinsurance.services.SerialNumbersQueryService;
import hn.com.tigo.equipmentinsurance.utils.ExceptionHandler;
import hn.com.tigo.equipmentinsurance.utils.ResponseBuilder;

@RestController
@RequestMapping("/serial-number-query")
public class SerialNumbersQueryController {

	private final SerialNumbersQueryService serialNumbersQueryService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public SerialNumbersQueryController(SerialNumbersQueryService serialNumbersQueryService) {
		super();
		this.serialNumbersQueryService = serialNumbersQueryService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);

	}

	@GetMapping()
	public ResponseEntity<Object> getSerialNumbersQuery(@RequestParam String itemCode,
			@RequestParam String warehouseCode, @RequestParam String subWarehouseCode,
			@RequestParam String inventoryType, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> serialNumbersQueryService.serialNumbersQuery(itemCode,
				warehouseCode, subWarehouseCode, inventoryType), request);

	}
}