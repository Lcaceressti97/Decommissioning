package hn.com.tigo.equipmentaccessoriesbilling.controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.equipmentaccessoriesbilling.models.ErrorTypeControlModel;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IErrorTypeControlService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.ResponseBuilder;

@RestController
@RequestMapping("/errortypecontrol")
public class ErrorTypeControlController {

	private final IErrorTypeControlService errorTypeControlService;
	private final ResponseBuilder responseBuilder;

	public ErrorTypeControlController(IErrorTypeControlService errorTypeControlService) {
		super();
		this.errorTypeControlService = errorTypeControlService;
		this.responseBuilder = new ResponseBuilder();
	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAll(HttpServletRequest request) {
		try {
			List<ErrorTypeControlModel> models = this.errorTypeControlService.getAll();
			return responseBuilder.buildSuccessResponse(request, models);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

}
