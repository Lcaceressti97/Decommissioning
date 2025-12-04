package hn.com.tigo.comodatos.controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.comodatos.models.ControlCancellationModel;
import hn.com.tigo.comodatos.services.interfaces.IControlCancellationService;
import hn.com.tigo.comodatos.utils.Util;

@RestController
@RequestMapping(path = "/control/cancellation")
public class ControlCancellationController {
	
	// Props
	private final IControlCancellationService controlCancellationService;
	private final Util util;
	
	public ControlCancellationController(IControlCancellationService controlCancellationService) {
		super();
		this.controlCancellationService = controlCancellationService;
		this.util = new Util();
	}
	
	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}
	
	@GetMapping("/all")
	public ResponseEntity<Object> getAllInvoicesByPagination() {
		List<ControlCancellationModel> model =  this.controlCancellationService.getAll();
		return ResponseEntity.ok(this.util.setSuccessResponse(model));
	}
	
	@PostMapping("/create")
	public ResponseEntity<Object> add(@Valid @RequestBody ControlCancellationModel model){
		
			this.controlCancellationService.add(model);
		
		return ResponseEntity.ok(this.util.setSuccessWithoutData());
	}

}
