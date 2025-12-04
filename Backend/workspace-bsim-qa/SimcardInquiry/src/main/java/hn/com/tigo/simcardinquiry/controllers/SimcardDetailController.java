package hn.com.tigo.simcardinquiry.controllers;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.simcardinquiry.exceptions.ExceptionHandler;
import hn.com.tigo.simcardinquiry.services.interfaces.ISimcardDetailService;
import hn.com.tigo.simcardinquiry.utils.ResponseBuilder;

@RestController
@RequestMapping("/simcarddetail")
public class SimcardDetailController {

	private final ISimcardDetailService simcardDetailService;
	private final ResponseBuilder responseBuilder;
	private final ExceptionHandler exceptionHandler;

	public SimcardDetailController(ISimcardDetailService simcardDetailService) {
		super();
		this.simcardDetailService = simcardDetailService;
		this.responseBuilder = new ResponseBuilder();
		this.exceptionHandler = new ExceptionHandler(responseBuilder);

	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping("/searchbyimsioricc/{value}/{type}")
	public ResponseEntity<?> getByImsiOrIcc(@PathVariable String value, @PathVariable int type,
			HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.simcardDetailService.findByImsiOrIcc(type, value), request);
	}

	@GetMapping("/{idSimcardPadre}")
	public ResponseEntity<Object> getSimcardDetailById(@PathVariable Long idSimcardPadre, HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> this.simcardDetailService.getSimcardDetailById(idSimcardPadre),
				request);

	}

	@PatchMapping("/updateStatus/{idSimcardPadre}")
	public ResponseEntity<Object> updateStatusSimcardDetails(@PathVariable Long idSimcardPadre,
			HttpServletRequest request) {

		return exceptionHandler.handleExceptions(() -> {
			this.simcardDetailService.updateStatusSimcardDetails(idSimcardPadre);
			return null;
		}, request);
	}
}
