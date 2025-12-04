package hn.com.tigo.comodatos.controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.comodatos.exceptions.BadRequestException;
import hn.com.tigo.comodatos.models.ConfigParametersModel;
import hn.com.tigo.comodatos.services.interfaces.IConfigParametersService;
import hn.com.tigo.comodatos.utils.Util;
import hn.com.tigo.comodatos.utils.ResponseBuilder;

@RestController
@RequestMapping("/configparameters")
public class ConfigParametersController {

	// Props
	private final IConfigParametersService configParametersService;
	private final Util util;
	private final ResponseBuilder responseBuilder;

	public ConfigParametersController(IConfigParametersService configParametersService) {
		// super();
		this.configParametersService = configParametersService;
		this.util = new Util();
		this.responseBuilder = new ResponseBuilder();
	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping("/searchById")
	public ResponseEntity<Object> getByStatus(@RequestParam Long idApplication, HttpServletRequest request) {

		try {
			List<ConfigParametersModel> models = configParametersService.getByIdApplication(idApplication);

			return this.responseBuilder.buildSuccessResponse(request, models);
		} catch (BadRequestException ex) {

			return this.responseBuilder.buildErrorNotDataResponse(ex, request);

		}

	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody ConfigParametersModel model,
			HttpServletRequest request) {

		try {
			this.configParametersService.update(id, model);
			return responseBuilder.buildSuccessResponse(request, model);
		} catch (BadRequestException ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}

	}

}
