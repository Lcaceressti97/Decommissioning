package hn.com.tigo.equipmentaccessoriesbilling.controllers;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hn.com.tigo.equipmentaccessoriesbilling.exceptions.ExceptionHandler;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.bsim.IWarehouseMaintenanceDao;
import hn.com.tigo.equipmentaccessoriesbilling.utils.ResponseBuilder;

@Controller
@RequestMapping("/warehousemaintenance")
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

	@GetMapping("/{userName}")
	public ResponseEntity<Object> getAllInvWarehouseBsim(@PathVariable String userName, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.warehouseMaintenanceDao.getInvWarehouseBsimByUser(userName),
				request);

	}

	@GetMapping("/searchbycode")
	public ResponseEntity<Object> getByWineryCode(@RequestParam String code, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.warehouseMaintenanceDao.findByCode(code), request);

	}

	@GetMapping("/report")
	public ResponseEntity<Object> getAllBranchOfficesReport(@RequestParam String user, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.warehouseMaintenanceDao.getWarehouseRepot(user), request);

	}

	@GetMapping("/namebycode")
	public ResponseEntity<Object> getWarehouseNameByCode(@RequestParam String code, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.warehouseMaintenanceDao.getWarehouseNameByCode(code),
				request);

	}

}
