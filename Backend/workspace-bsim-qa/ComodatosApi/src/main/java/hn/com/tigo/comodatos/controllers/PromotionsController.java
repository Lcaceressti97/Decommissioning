package hn.com.tigo.comodatos.controllers;

import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
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

import hn.com.tigo.comodatos.exceptions.ExceptionHandler;
import hn.com.tigo.comodatos.models.PromotionsModel;
import hn.com.tigo.comodatos.responses.DescTotal;
import hn.com.tigo.comodatos.services.interfaces.IPromotionsService;
import hn.com.tigo.comodatos.soap.request.PromotionsRequest;
import hn.com.tigo.comodatos.utils.ResponseBuilder;

@RestController
@RequestMapping("/promotions")
public class PromotionsController {

	private final IPromotionsService promotionsService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public PromotionsController(IPromotionsService promotionsService) {
		super();
		this.promotionsService = promotionsService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);

	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAllPromotions(HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.promotionsService.getAllPromotions(), request);

	}

	/*
	 * Rebeca 2024 September 
	 */
	@GetMapping("promotions{modelCode}")
	public ResponseEntity<Object> getPromotionsByModelCode(@PathVariable String modelCode, HttpServletRequest request) {
		return exceptionHandler.handleExceptions(() -> 
									this.promotionsService.getPromotionsByModelCode(modelCode),
									request);

	}

	/*
	 * Rebeca 2024 September  el post
	 */
	@PostMapping("promotionsl")
	//public List<PromotionsDetailEntity> buscarModelos(@Valid @RequestBody PromotionsRequest model,
	public ResponseEntity<Object> buscarModelos(@Valid @RequestBody PromotionsRequest model,
			HttpServletRequest httpRequest) {
		
		return exceptionHandler.handleExceptions(() ->
		this.promotionsService.buscarModelos(model.getPrecio_promo(), 
				model.getMeses_permanencia(),
				model.getCodigo_modelo()), httpRequest);
	
	}
	
	@PostMapping("allowance")
	//public List<PromotionsDetailEntity> buscarModelos(@Valid @RequestBody PromotionsRequest model,
	public ResponseEntity<Object> getDesc(@Valid @RequestBody PromotionsRequest model,
			HttpServletRequest httpRequest) {

		return exceptionHandler.handleExceptions(() ->
		new DescTotal((BigDecimal) this.promotionsService.getDesc(
				model.getPrecio_promo(), 
				model.getMeses_permanencia(),
				model.getCodigo_modelo(),
				model.getTipo_cliente(),
				model.getCurrent_date()
				)), httpRequest);
	
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getPromotionsById(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.promotionsService.getPromotionsById(id), request);

	}
	
	//ejemplo
	@PostMapping("/add")
	public ResponseEntity<Object> addPromotions(@Valid @RequestBody PromotionsModel model,
			HttpServletRequest httpRequest) {

		return exceptionHandler.handleExceptions(() -> {
			this.promotionsService.addPromotions(model);
			return model;
		}, httpRequest);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> updatePromotions(@PathVariable Long id, @Valid @RequestBody PromotionsModel model,
			HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.promotionsService.updatePromotions(id, model);
			return model;
		}, request);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletePromotions(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.promotionsService.deletePromotions(id);
			return null;
		}, request);
	}
}
