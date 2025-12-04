package hn.com.tigo.comodatos.controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.comodatos.models.CancelMooringModel;
import hn.com.tigo.comodatos.models.MooringBillingModel;
import hn.com.tigo.comodatos.services.interfaces.IControlCancellationService;
import hn.com.tigo.comodatos.services.interfaces.IMooringBillingService;
import hn.com.tigo.comodatos.utils.ResponseBuilder;
import hn.com.tigo.comodatos.utils.Util;
import hn.com.tigo.comodatos.utils.ValidateParam;

@RestController
@RequestMapping(path = "/morringbilling")
public class MooringBillingController {

	// Props
	private final IMooringBillingService iMooringBillingService;
	private final IControlCancellationService iControlCancellationService;
	private final Util util;
	private final ResponseBuilder responseBuilder;
	
	public MooringBillingController(IMooringBillingService iMooringBillingService, IControlCancellationService iControlCancellationService) {
		super();
		this.iMooringBillingService = iMooringBillingService;
		this.iControlCancellationService = iControlCancellationService;
		this.util = new Util();
		this.responseBuilder = new ResponseBuilder();
	}
	
	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}
	
	@GetMapping("/bypagination")
	public Page<MooringBillingModel> getAllInvoicesByPagination(Pageable pageable) {
		return this.iMooringBillingService.getAllCmdByPagination(pageable);
	}
	
	@GetMapping("/findbyfilter/{value}/{type}")
	public ResponseEntity<Object> findByFilter(@PathVariable String value, @PathVariable int type) {
		List<MooringBillingModel> models = this.iMooringBillingService.findByFilter(type, value);
		return ResponseEntity.ok(this.util.setSuccessResponse(models));
	}
	
	@GetMapping("/findbyconsult/{value}/{type}")
	public ResponseEntity<Object> findByConsult(@PathVariable Long value, @PathVariable int type) {
		List<MooringBillingModel> models = this.iMooringBillingService.findByConsult(type, value);
		return ResponseEntity.ok(this.util.setSuccessResponse(models));
	}
	
	@PostMapping("/create")
	public ResponseEntity<Object> add(@Valid @RequestBody MooringBillingModel modell,HttpServletRequest request){
		
		
		Long id = this.iMooringBillingService.add(modell);
		
		MooringBillingModel data = this.iMooringBillingService.findById(id);
		
		return ResponseEntity.ok(this.util.setSuccessResponse(data));
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody MooringBillingModel model) {
		this.iMooringBillingService.update(id, model);
		return ResponseEntity.ok(this.util.setSuccessWithoutData());
	}
	
	@PutMapping("/cancel")
	public ResponseEntity<Object> cancel(@Valid @RequestBody CancelMooringModel model) {
		
		ValidateParam.type(model.getType());
		
		final boolean validateParam = model.getType().equals("subscriber");
		
		
		if(validateParam) {
			ValidateParam.value(model.getSubscriber(), model.getType());
			
		}else {
			ValidateParam.value(model.getBillingAccount(), model.getType());
		}

		CancelMooringModel responseBody = this.iControlCancellationService.cancel(model);
		
		//this.iMooringBillingService.update(id, model);
		return ResponseEntity.ok(this.util.setSuccessResponse(responseBody));
	}
	
}
