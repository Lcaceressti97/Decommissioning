package hn.com.tigo.equipmentaccessoriesbilling.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
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

import hn.com.tigo.equipmentaccessoriesbilling.models.DesktopSalesTransactionsModel;
import hn.com.tigo.equipmentaccessoriesbilling.models.GeneralError;
import hn.com.tigo.equipmentaccessoriesbilling.models.GeneralResponse;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IDesktopSalesTransactionsService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.ResponseBuilder;
import hn.com.tigo.equipmentaccessoriesbilling.utils.Util;

@RestController
@RequestMapping("/desktopsalestransactions")
public class DesktopSalesTransactionsController {

	private final IDesktopSalesTransactionsService desktopSalesTransactionsService;
	private final ResponseBuilder responseBuilder;

	public DesktopSalesTransactionsController(IDesktopSalesTransactionsService desktopSalesTransactionsService) {
		super();
		this.desktopSalesTransactionsService = desktopSalesTransactionsService;
		this.responseBuilder = new ResponseBuilder();
	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAllByPagination(Pageable pageable, HttpServletRequest request) {
		try {
			Page<DesktopSalesTransactionsModel> getAllByPagination = desktopSalesTransactionsService
					.getAllByPagination(pageable);
			return responseBuilder.buildSuccessResponse(request, getAllByPagination);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable Long id, HttpServletRequest request) {
		try {
			DesktopSalesTransactionsModel model = this.desktopSalesTransactionsService.getById(id);
			return responseBuilder.buildSuccessResponse(request, model);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@GetMapping("/getbyclosingdateandseller")
	public ResponseEntity<Object> getByClosingDateAndSeller(
			@RequestParam(value = "closingDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> closingDate,
			@RequestParam(required = false) Long seller, HttpServletRequest request) {

		try {
			List<DesktopSalesTransactionsModel> models = this.desktopSalesTransactionsService
					.findByClosingDateAndSeller(closingDate, seller);
			return responseBuilder.buildSuccessResponse(request, models);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@PostMapping("/add")
	public ResponseEntity<Object> add(@Valid @RequestBody DesktopSalesTransactionsModel model,
			HttpServletRequest request) {

		try {
			this.desktopSalesTransactionsService.add(model);
			return responseBuilder.buildSuccessResponse(request, model);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody DesktopSalesTransactionsModel model,
			HttpServletRequest request) {

		try {
			this.desktopSalesTransactionsService.update(id, model);
			return responseBuilder.buildSuccessResponse(request, model);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@PutMapping("/updatechargeamount/{id}")
	public ResponseEntity<Object> updateChargeAmount(@PathVariable Long id, @RequestParam Double chargeAmount,
			@RequestParam String closingDate, @RequestParam Long seller, HttpServletRequest request) {

		try {
			DesktopSalesTransactionsModel model = this.desktopSalesTransactionsService.getById(id);
			LocalDateTime closingDateTime = LocalDateTime.parse(closingDate);

			if (model == null || !model.getClosingDate().equals(closingDateTime) || !model.getSeller().equals(seller)) {

				GeneralError error = new GeneralError();
				error.setCode("400");
				error.setUserMessage("Invalid input parameters");
				error.setMoreInfo("The provided closingDate or seller do not match the record with ID: " + id);
				error.setInternalMessage("Invalid input parameters");

				GeneralResponse gr = new Util().setErrorResponse(error);
				return ResponseEntity.badRequest().body(gr);
			}

			this.desktopSalesTransactionsService.updateChargeAmount(id, chargeAmount, closingDateTime, seller);
			return responseBuilder.buildSuccessResponse(request, model);

		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {
		this.desktopSalesTransactionsService.delete(id);

		try {
			this.desktopSalesTransactionsService.delete(id);
			return responseBuilder.buildSuccessResponse(request, null);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

}
