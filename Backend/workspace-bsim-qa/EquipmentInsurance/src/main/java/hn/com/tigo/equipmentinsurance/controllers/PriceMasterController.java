package hn.com.tigo.equipmentinsurance.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.equipmentinsurance.models.PriceMasterModel;
import hn.com.tigo.equipmentinsurance.services.interfaces.IPriceMasterService;
import hn.com.tigo.equipmentinsurance.utils.ExceptionHandler;
import hn.com.tigo.equipmentinsurance.utils.ResponseBuilder;

@RestController
@RequestMapping("/price-master")
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

	@GetMapping()
	public ResponseEntity<Object> getPriceMaster(HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> priceMasterService.getPriceMaster(), request);

	}
	
	@GetMapping("/inventory/{inventoryType}/model/{model}")
	public ResponseEntity<Object> getByInventoryTypeAndModel(@PathVariable String inventoryType,@PathVariable String model, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.priceMasterService.findByInventoryTypeAndModel(inventoryType,model), request);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getPriceMasterById(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.priceMasterService.getPriceMasterById(id), request);

	}

	@PostMapping("/add")
	public ResponseEntity<Object> createdPriceMaster(@Valid @RequestBody PriceMasterModel model,
			HttpServletRequest httpRequest) {

		return exceptionHandler.handleExceptions(() -> {
			this.priceMasterService.createdPriceMaster(model);
			return model;
		}, httpRequest);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updatePriceMaster(@PathVariable Long id, @Valid @RequestBody PriceMasterModel model,
			HttpServletRequest httpRequest) {

		return exceptionHandler.handleExceptions(() -> {
			this.priceMasterService.updatePriceMaster(id, model);
			return model;
		}, httpRequest);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletePriceMaster(@PathVariable Long id, HttpServletRequest httpRequest) {

		return exceptionHandler.handleExceptions(() -> {
			this.priceMasterService.deletePriceMaster(id);
			return null;
		}, httpRequest);
	}
}
