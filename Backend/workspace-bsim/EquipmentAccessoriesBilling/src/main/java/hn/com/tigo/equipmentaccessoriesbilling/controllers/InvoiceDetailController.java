package hn.com.tigo.equipmentaccessoriesbilling.controllers;

import java.util.List;

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

import hn.com.tigo.equipmentaccessoriesbilling.models.InvoiceDetailModel;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IInvoiceDetailService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.ResponseBuilder;

@RestController
@RequestMapping("/invoicedetail")
public class InvoiceDetailController {

	private final IInvoiceDetailService invoiceDetailService;
	private final ResponseBuilder responseBuilder;

	public InvoiceDetailController(IInvoiceDetailService invoiceDetailService) {
		super();
		this.invoiceDetailService = invoiceDetailService;
		this.responseBuilder = new ResponseBuilder();
	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAll(HttpServletRequest request) {

		try {
			List<InvoiceDetailModel> models = this.invoiceDetailService.getAll();
			return responseBuilder.buildSuccessResponse(request, models);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable Long id, HttpServletRequest request) {

		try {
			InvoiceDetailModel model = this.invoiceDetailService.getById(id);
			return responseBuilder.buildSuccessResponse(request, model);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@GetMapping("/details/{idInvoice}")
	public ResponseEntity<Object> getDetailByIdInvoice(@PathVariable Long idInvoice, HttpServletRequest request) {
		try {
			List<InvoiceDetailModel> models = this.invoiceDetailService.getDetailByIdInvoice(idInvoice);
			return responseBuilder.buildSuccessResponse(request, models);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@PostMapping("/add")
	public ResponseEntity<Object> add(@Valid @RequestBody InvoiceDetailModel model, HttpServletRequest request) {
		try {
			this.invoiceDetailService.add(model);
			return responseBuilder.buildSuccessResponse(request, model);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody InvoiceDetailModel model,
			HttpServletRequest request) {

		try {
			this.invoiceDetailService.update(id, model);
			return responseBuilder.buildSuccessResponse(request, model);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {

		try {
			this.invoiceDetailService.delete(id);
			return responseBuilder.buildSuccessResponse(request, null);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}
}
