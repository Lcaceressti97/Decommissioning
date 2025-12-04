package hn.com.tigo.equipmentinsurance.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import hn.com.tigo.equipmentinsurance.repositories.bsim.IExistencesDao;
import hn.com.tigo.equipmentinsurance.utils.ExceptionHandler;
import hn.com.tigo.equipmentinsurance.utils.ResponseBuilder;

@Controller
@RequestMapping("/existences")
public class ExistencesController {

	private final IExistencesDao existencesDao;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public ExistencesController(IExistencesDao existencesDao) {
		super();
		this.existencesDao = existencesDao;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);
	}

	@GetMapping("/{warehouseId}/{typeInventoryId}")
	public ResponseEntity<Object> getExistencesByFilter(@PathVariable Long warehouseId,
			@PathVariable Long typeInventoryId, HttpServletRequest request) {
		return exceptionHandler.handleExceptions(
				() -> this.existencesDao.getExistencesByFilter(warehouseId, typeInventoryId), request);
	}

	@GetMapping("/view/{warehouseId}/{type}/{equipmentLine}")
	public ResponseEntity<Object> getExistencesViewByFilter(@PathVariable String warehouseId, @PathVariable String type,
			@PathVariable String equipmentLine, HttpServletRequest request) {
		if (!warehouseId.matches("\\d+")) {
			throw new RuntimeException(String.format("The value of '%s' is not valid for warehouse id", warehouseId));
		}
		Long warehouseIdLong = Long.parseLong(warehouseId);
		return exceptionHandler.handleExceptions(
				() -> this.existencesDao.getExistencesViewByFilter(warehouseIdLong, type, equipmentLine), request);
	}

}
