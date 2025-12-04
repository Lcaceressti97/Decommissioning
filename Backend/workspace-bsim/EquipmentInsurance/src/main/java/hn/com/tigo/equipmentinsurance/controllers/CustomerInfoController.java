package hn.com.tigo.equipmentinsurance.controllers;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.equipmentinsurance.models.ConfigParametersModel;
import hn.com.tigo.equipmentinsurance.provider.CustomerInfoProvider;
import hn.com.tigo.equipmentinsurance.services.interfaces.IConfigParametersService;
import hn.com.tigo.equipmentinsurance.utils.ExceptionHandler;
import hn.com.tigo.equipmentinsurance.utils.ResponseBuilder;



@RestController
@RequestMapping(path = "customer-info")
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

	@GetMapping("/{serviceAccount}")
	public ResponseEntity<Object> getByServiceAccount(@PathVariable String serviceAccount, HttpServletRequest request) {
		ConfigParametersModel configModel = this.configParametersService.getByName("CBS_QUERY_CUSTOMER_INFO_TASK_URL");
		return exceptionHandler.handleExceptions(() -> this.customerInfoProvider.getCustomerInfoByServiceAccount(serviceAccount, configModel.getParameterValue()),
				request);

	}

}
