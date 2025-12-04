package hn.com.tigo.equipmentaccessoriesbilling.controllers;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hn.com.tigo.equipmentaccessoriesbilling.exceptions.ExceptionHandler;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.bsim.ISubWarehouseDao;
import hn.com.tigo.equipmentaccessoriesbilling.utils.ResponseBuilder;

@Controller
@RequestMapping("/subwarehouses")
public class SubWarehouseController {

	private final ISubWarehouseDao subWarehouseDao;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public SubWarehouseController(ISubWarehouseDao subWarehouseDao) {
		super();
		this.subWarehouseDao = subWarehouseDao;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);

	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAll(HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.subWarehouseDao.getAll(), request);

	}
}
