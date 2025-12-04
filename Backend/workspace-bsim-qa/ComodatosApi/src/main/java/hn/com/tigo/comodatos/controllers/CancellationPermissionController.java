package hn.com.tigo.comodatos.controllers;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.comodatos.models.CancellationPermissionModel;
import hn.com.tigo.comodatos.models.GeneralError;
import hn.com.tigo.comodatos.models.GeneralResponse;
import hn.com.tigo.comodatos.services.interfaces.ICancellationPermissionService;
import hn.com.tigo.comodatos.utils.ResponseBuilder;
import hn.com.tigo.comodatos.utils.Util;

@RestController
@RequestMapping(path = "/cancellation/permission")
public class CancellationPermissionController {
	
	// Props
	private final ICancellationPermissionService cancellationPermissionService;
	private final Util util;
	private final ResponseBuilder responseBuilder;

	public CancellationPermissionController(ICancellationPermissionService cancellationPermissionService) {
		super();
		this.cancellationPermissionService = cancellationPermissionService;
		this.util = new Util();
		this.responseBuilder = new ResponseBuilder();
	}
	
	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<Object> getAllInvoicesByPagination(@PathVariable Long id, HttpServletRequest request) {
		try {
			CancellationPermissionModel model =  this.cancellationPermissionService.findById(id);
			
			return this.responseBuilder.buildSuccessResponse(request, model);
		}catch(Exception ex) {
			return this.responseBuilder.buildErrorResponse(ex, request);
		}

	}
	
	@GetMapping("/username/{username}")
	public ResponseEntity<Object> findByFilter(@PathVariable String username, HttpServletRequest request) {
		
		try {
			CancellationPermissionModel model = this.cancellationPermissionService.findByUserName(username);
				return this.responseBuilder.buildSuccessResponse(request, model);
		}catch(Exception ex) {
			return this.responseBuilder.buildErrorResponse(ex, request);
		}

	}
	
	@PostMapping("/create")
	public ResponseEntity<Object> add(@Valid @RequestBody CancellationPermissionModel model){
		
		try {
			this.cancellationPermissionService.add(model);
			
			return ResponseEntity.ok(this.util.setSuccessWithoutData());
		}catch(Exception ex) {
			GeneralError error = new GeneralError();
			error.setCode("400");
			error.setUserMessage(ex.getMessage());
			error.setMoreInfo("Record with username "+  model.getUserName() + " exists" );
			error.setInternalMessage("check the log for more information");

			GeneralResponse gr = new Util().setErrorResponse(error);
			return ResponseEntity.badRequest().body(gr);
		}

	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody CancellationPermissionModel model, HttpServletRequest request) {
		
		try {
			this.cancellationPermissionService.update(id, model);
			return this.responseBuilder.buildSuccessResponse(request, model);
		}catch(Exception ex) {
			return this.responseBuilder.buildErrorResponse(ex, request);
		}

	}
	
}
