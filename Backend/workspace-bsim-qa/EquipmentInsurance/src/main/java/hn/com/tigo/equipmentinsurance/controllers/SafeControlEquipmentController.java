package hn.com.tigo.equipmentinsurance.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import hn.com.tigo.equipmentinsurance.models.SafeControlEquipmentModel;
import hn.com.tigo.equipmentinsurance.services.interfaces.ISafeControlEquipmentService;
import hn.com.tigo.equipmentinsurance.utils.Util;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/safecontrolequipment")
public class SafeControlEquipmentController {

	private final ISafeControlEquipmentService safeControlEquipmentService;
	private final Util util;

	public SafeControlEquipmentController(ISafeControlEquipmentService safeControlEquipmentService) {
		this.safeControlEquipmentService = safeControlEquipmentService;
		this.util = new Util();
	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAll() {
		List<SafeControlEquipmentModel> models = this.safeControlEquipmentService.getAll();
		return ResponseEntity.ok(this.util.setSuccessResponse(models));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable Long id) {
		SafeControlEquipmentModel model = this.safeControlEquipmentService.getById(id);
		return ResponseEntity.ok(this.util.setSuccessResponse(model));
	}

	@GetMapping("/searchbyphone")
	public ResponseEntity<Object> getSafeControlEquipmentByPhone(
			@RequestParam(value = "phone", required = false) String phone) {

		List<SafeControlEquipmentModel> models = this.safeControlEquipmentService.getByPhone(phone);
		return ResponseEntity.ok(this.util.setSuccessResponse(models));
	}

	@PostMapping("/add")
	public ResponseEntity<Object> add(@Valid @RequestBody SafeControlEquipmentModel model) {
		this.safeControlEquipmentService.add(model);
		return ResponseEntity.ok(this.util.setSuccessWithoutData());
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody SafeControlEquipmentModel model) {
		this.safeControlEquipmentService.update(id, model);
		return ResponseEntity.ok(this.util.setSuccessWithoutData());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		this.safeControlEquipmentService.delete(id);
		return ResponseEntity.ok(this.util.setSuccessWithoutData());
	}

}
