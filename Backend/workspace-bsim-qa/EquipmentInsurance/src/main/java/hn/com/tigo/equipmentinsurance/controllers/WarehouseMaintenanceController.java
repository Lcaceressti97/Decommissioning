package hn.com.tigo.equipmentinsurance.controllers;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hn.com.tigo.equipmentinsurance.repositories.bsim.IWarehouseMaintenanceDao;
import hn.com.tigo.equipmentinsurance.utils.ExceptionHandler;
import hn.com.tigo.equipmentinsurance.utils.ResponseBuilder;

@Controller
@RequestMapping("/warehouse-maintenance")
public class WarehouseMaintenanceController {

	private final IWarehouseMaintenanceDao warehouseMaintenanceDao;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public WarehouseMaintenanceController(IWarehouseMaintenanceDao warehouseMaintenanceDao) {
		super();
		this.warehouseMaintenanceDao = warehouseMaintenanceDao;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);

	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAll(HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.warehouseMaintenanceDao.getAll(), request);

	}

}
