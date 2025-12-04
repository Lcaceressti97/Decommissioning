package hn.com.tigo.equipmentaccessoriesbilling.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
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
import org.springframework.web.server.ResponseStatusException;

import hn.com.tigo.equipmentaccessoriesbilling.exceptions.ExceptionHandler;
import hn.com.tigo.equipmentaccessoriesbilling.models.PriceMasterDTO;
import hn.com.tigo.equipmentaccessoriesbilling.models.PriceMasterModel;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IPriceMasterService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.ResponseBuilder;

@RestController
@RequestMapping("/pricemaster")
public class PriceMasterController {

	private final IPriceMasterService priceMasterService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public PriceMasterController(IPriceMasterService priceMasterService) {
		super();
		this.priceMasterService = priceMasterService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);

	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAll(Pageable pageable,HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> priceMasterService.getAll(pageable), request);

	}

	@GetMapping("/getall")
	public ResponseEntity<Object> getAllPriceMaster(HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> priceMasterService.getAllPriceMaster(), request);

	}

	@GetMapping("/model/{model}")
	public ResponseEntity<Object> getByModel(@PathVariable String model, HttpServletRequest request) {
		try {
			List<PriceMasterDTO> priceMasterModel = this.priceMasterService.getPriceMasterByModel(model);
			return responseBuilder.buildSuccessResponse(request, priceMasterModel);
		} catch (ResponseStatusException ex) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("code", 400);
			errorResponse.put("description", "Item not available");
			errorResponse.put("data", "Bad Request");
			List<Map<String, Object>> errors = new ArrayList<>();
			Map<String, Object> errorDetails = new HashMap<>();
			errorDetails.put("userMessage", ex.getReason());
			errorDetails.put("moreInfo", "Contacta al administrador para más información");
			errorDetails.put("internalMessage", "Revisa los registros para más información");
			errors.add(errorDetails);
			errorResponse.put("errors", errors);
			return ResponseEntity.status(ex.getStatus()).body(errorResponse);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}

	}

	@GetMapping("/maintenance/{model}")
	public ResponseEntity<Object> getPriceMasterModelByModel(@PathVariable String model,HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> priceMasterService.getPriceMasterModelByModel(model), request);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.priceMasterService.getPriceMasterById(id), request);

	}
	
	@GetMapping("/{model}/{inventoryType}")
	public ResponseEntity<Object> getPriceMasterByModelAndInventoryType(@PathVariable String model,@PathVariable String inventoryType, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.priceMasterService.getPriceMasterByModelAndInventoryType(model,inventoryType), request);

	}

	@PostMapping("/add")
	public ResponseEntity<Object> add(@Valid @RequestBody PriceMasterModel model, HttpServletRequest httpRequest) {

		return exceptionHandler.handleExceptions(() -> {
			this.priceMasterService.addPriceMaster(model);
			return model;
		}, httpRequest);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody PriceMasterModel model,
			HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.priceMasterService.updatePriceMaster(id, model);
			return model;
		}, request);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.priceMasterService.deletePriceMaster(id);
			return null;
		}, request);
	}

}
