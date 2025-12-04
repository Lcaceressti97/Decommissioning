package hn.com.tigo.equipmentaccessoriesbilling.controllers;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.equipmentaccessoriesbilling.exceptions.ExceptionHandler;
import hn.com.tigo.equipmentaccessoriesbilling.models.ConfigParametersModel;
import hn.com.tigo.equipmentaccessoriesbilling.provider.CustomerInfoProvider;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IConfigParametersService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.ResponseBuilder;

@RestController
@RequestMapping(path = "customerinfo")
public class CustomerInfoController {

	private final CustomerInfoProvider customerInfoProvider;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;
	private final IConfigParametersService configParametersService;

	public CustomerInfoController(CustomerInfoProvider customerInfoProvider, IConfigParametersService configParametersService) {
		super();
		this.customerInfoProvider = customerInfoProvider;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);
		this.configParametersService = configParametersService;
	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping("/{acctCode}")
	public ResponseEntity<Object> getByAcctCode(@PathVariable String acctCode, HttpServletRequest request) {
		ConfigParametersModel configModel = this.configParametersService.getByName("CBS_QUERY_CUSTOMER_INFO_TASK_URL");
		return exceptionHandler.handleExceptions(() -> this.customerInfoProvider.getCustomerInfoByAcctCode(acctCode, configModel.getParameterValue()),
				request);

	}
}
