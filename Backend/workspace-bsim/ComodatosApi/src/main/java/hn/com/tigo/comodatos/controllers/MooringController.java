package hn.com.tigo.comodatos.controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.comodatos.models.MooringInfoModel;
import hn.com.tigo.comodatos.models.MooringModel;
import hn.com.tigo.comodatos.services.interfaces.MooringService;
import hn.com.tigo.comodatos.utils.Util;

@RestController
@RequestMapping(path = "/mooring")
public class MooringController {

	// Props
	private final MooringService mooringService;
	private final Util util;
	
	public MooringController(MooringService mooringService) {
		super();
		this.mooringService = mooringService;
		this.util = new Util();
	}
	
	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<Object> getAllInvoicesByPagination(@PathVariable Long id) {
		MooringModel model =  this.mooringService.findById(id);
		return ResponseEntity.ok(this.util.setSuccessResponse(model));
	}
	
	@GetMapping("/findby/idmooringbilling/{id}")
	public ResponseEntity<Object> findByFilter(@PathVariable Long id) {
		List<MooringModel> models = this.mooringService.findByIdMooringBilling(id);
		return ResponseEntity.ok(this.util.setSuccessResponse(models));
	}
	
	@GetMapping("/validate/subscriber/{subcriber}")
	public ResponseEntity<Object> validateSubcriber(@PathVariable String subcriber) {
		this.mooringService.validateSubcriber(subcriber);
		return ResponseEntity.ok(this.util.setSuccessWithoutData());
	}
	
	// toDo
	@GetMapping("/info/subscriber/{subcriber}")
	public ResponseEntity<Object> getInfoBySubscriber(@PathVariable String subcriber) {
		MooringInfoModel info = this.mooringService.getInfoMooringBySubscriber(subcriber);
		return ResponseEntity.ok(this.util.setSuccessResponse(info));
	}
	
	@PostMapping("/create/test/{id}")
	public ResponseEntity<Object> addTest(@PathVariable Long id,@Valid @RequestBody MooringModel model){
		
			this.mooringService.add(id,model);
		
		return ResponseEntity.ok(this.util.setSuccessWithoutData());
	}
	
	@PostMapping("/create/idmooringbilling/{id}")
	public ResponseEntity<Object> add(@PathVariable Long id,@Valid @RequestBody List<MooringModel> model){
		
		try {
			this.mooringService.addTest(id,model);
			
			return ResponseEntity.ok(this.util.setSuccessWithoutData());
		}catch(Exception ex) {
			return ResponseEntity.status(400).body(this.util.setError(ex, "Contact to admin for more information"));
		}
			
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody MooringModel model) {
		this.mooringService.update(id, model);
		return ResponseEntity.ok(this.util.setSuccessWithoutData());
	}
	
	@DeleteMapping("/delete/id/{id}/status/{status}")
	public ResponseEntity<Object> delete(@PathVariable Long id, @PathVariable int status) {
		this.mooringService.delete(id, status);
		return ResponseEntity.ok(this.util.setSuccessWithoutData());
	}
	
}
