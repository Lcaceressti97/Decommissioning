package hn.com.tigo.inquiriesamsysnavega.controllers;

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

import hn.com.tigo.inquiriesamsysnavega.models.NavegaBankModel;
import hn.com.tigo.inquiriesamsysnavega.services.interfaces.INavegaBankService;
import hn.com.tigo.inquiriesamsysnavega.utils.ResponseBuilder;

@RestController
@RequestMapping("/navegabank")
public class NavegaBankController {

	private final INavegaBankService navegaBankService;
	private final ResponseBuilder responseBuilder;

	public NavegaBankController(INavegaBankService navegaBankService) {
		super();
		this.navegaBankService = navegaBankService;
		this.responseBuilder = new ResponseBuilder();
	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAll(HttpServletRequest request) {
		try {
			List<NavegaBankModel> listModel = this.navegaBankService.getAllNavegaBank();
			return responseBuilder.buildSuccessResponse(request, listModel);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getById(@PathVariable Long id, HttpServletRequest request) {

		try {
			NavegaBankModel model = this.navegaBankService.getNavegaBankById(id);
			return responseBuilder.buildSuccessResponse(request, model);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@GetMapping("/identifyngletter/{identifyingLetter}")
	public ResponseEntity<Object> getBankByIdentifyingLetter(@PathVariable String identifyingLetter,
			HttpServletRequest request) {

		try {
			NavegaBankModel model = this.navegaBankService.getBankByIdentifyingLetter(identifyingLetter);
			return responseBuilder.buildSuccessResponse(request, model);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@PostMapping("/add")
	public ResponseEntity<Object> add(@Valid @RequestBody NavegaBankModel model, HttpServletRequest request) {

		try {
			this.navegaBankService.addNavegaBank(model);
			return responseBuilder.buildSuccessResponse(request, model);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody NavegaBankModel model,
			HttpServletRequest request) {

		try {
			this.navegaBankService.updateNavegaBank(id, model);
			return responseBuilder.buildSuccessResponse(request, model);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {

		try {
			this.navegaBankService.deleteNavegaBank(id);
			return responseBuilder.buildSuccessResponse(request, null);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

}
