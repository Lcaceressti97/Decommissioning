package hn.com.tigo.equipmentaccessoriesbilling.controllers;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.equipmentaccessoriesbilling.exceptions.ExceptionHandler;
import hn.com.tigo.equipmentaccessoriesbilling.models.CancelInvoiceWithFiscalNoModel;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.ICancelInvoiceWithFiscalNoService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.ResponseBuilder;

@RestController
@RequestMapping("/cancelinvoicewithfiscalno")
public class CancelInvoiceWithFiscalNoController {

	private final ICancelInvoiceWithFiscalNoService cancelInvoiceWithFiscalNoService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public CancelInvoiceWithFiscalNoController(ICancelInvoiceWithFiscalNoService cancelInvoiceWithFiscalNoService) {
		super();
		this.cancelInvoiceWithFiscalNoService = cancelInvoiceWithFiscalNoService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);

	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAll(Pageable pageable, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> cancelInvoiceWithFiscalNoService.getAll(pageable), request);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.cancelInvoiceWithFiscalNoService.getById(id), request);

	}

	@GetMapping("/searchbyusername")
	public ResponseEntity<Object> getByUserCreate(@RequestParam String username, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> cancelInvoiceWithFiscalNoService.getByUserName(username),
				request);

	}

	@PostMapping("/add")
	public ResponseEntity<Object> add(@Valid @RequestBody CancelInvoiceWithFiscalNoModel model,
			HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.cancelInvoiceWithFiscalNoService.add(model);
			return model;
		}, request);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id,
			@Valid @RequestBody CancelInvoiceWithFiscalNoModel model, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.cancelInvoiceWithFiscalNoService.update(id, model);
			return model;
		}, request);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.cancelInvoiceWithFiscalNoService.delete(id);
			return null;
		}, request);
	}

}