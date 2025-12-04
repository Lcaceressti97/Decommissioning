package hn.com.tigo.equipmentinsurance.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import hn.com.tigo.equipmentinsurance.models.CalculateOutstandingFeesRequest;
import hn.com.tigo.equipmentinsurance.models.EquipmentInsuranceControlModel;
import hn.com.tigo.equipmentinsurance.services.interfaces.IEquipmentInsuranceControlService;
import hn.com.tigo.equipmentinsurance.utils.ExceptionHandler;
import hn.com.tigo.equipmentinsurance.utils.ResponseBuilder;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/equipment-insurance-control")
public class EquipmentInsuranceControlController {

	private final IEquipmentInsuranceControlService equipmentInsuranceControlService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public EquipmentInsuranceControlController(IEquipmentInsuranceControlService equipmentInsuranceControlService) {
		this.equipmentInsuranceControlService = equipmentInsuranceControlService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);
	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAll(Pageable pageable,HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> equipmentInsuranceControlService.getAll(pageable), request);

	}

	@GetMapping("/phone/{phoneNumber}")
	public ResponseEntity<Object> getSafeControlEquipmentByPhone(@PathVariable String phoneNumber,
			HttpServletRequest request) {

		return exceptionHandler
				.handleExceptions(() -> this.equipmentInsuranceControlService.getByPhoneNumber(phoneNumber), request);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.equipmentInsuranceControlService.getById(id), request);

	}

	@GetMapping("/esn/{esn}")
	public ResponseEntity<Object> getByEsn(@PathVariable String esn, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.equipmentInsuranceControlService.getByEsn(esn), request);

	}

	@PostMapping("/add")
	public ResponseEntity<Object> add(@Valid @RequestBody EquipmentInsuranceControlModel model,
			HttpServletRequest httpRequest) {

		return exceptionHandler.handleExceptions(() -> {
			this.equipmentInsuranceControlService.add(model);
			return model;
		}, httpRequest);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id,
			@Valid @RequestBody EquipmentInsuranceControlModel model, HttpServletRequest httpRequest) {

		return exceptionHandler.handleExceptions(() -> {
			this.equipmentInsuranceControlService.update(id, model);
			return model;
		}, httpRequest);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest httpRequest) {

		return exceptionHandler.handleExceptions(() -> {
			this.equipmentInsuranceControlService.delete(id);
			return null;
		}, httpRequest);
	}

	@PostMapping("/calculate-outstanding-fees")
	public ResponseEntity<Object> getCalculateOutstandingFees(@Valid @RequestBody CalculateOutstandingFeesRequest model,
			HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> equipmentInsuranceControlService.calculateOutstandingFees(model),
				request);
	}

}
