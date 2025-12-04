package hn.com.tigo.inquiriesamsysnavega.controllers;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.inquiriesamsysnavega.models.NavegaBalancesModel;
import hn.com.tigo.inquiriesamsysnavega.models.TableNameModel;
import hn.com.tigo.inquiriesamsysnavega.services.interfaces.NavegaBalancesService;
import hn.com.tigo.inquiriesamsysnavega.utils.ResponseBuilder;

@RestController
@RequestMapping("/navegabalances")
public class NavegaBalancesController {

	private final NavegaBalancesService navegaBalancesService;
	private final ResponseBuilder responseBuilder;

	public NavegaBalancesController(NavegaBalancesService navegaBalancesService) {
		super();
		this.navegaBalancesService = navegaBalancesService;
		this.responseBuilder = new ResponseBuilder();
	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping()
	public ResponseEntity<Object> getAllNavegaBalances(HttpServletRequest request) {

		try {
			List<NavegaBalancesModel> pages = navegaBalancesService.getAllNavegaBalances();
			return responseBuilder.buildSuccessResponse(request, pages);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@GetMapping("/tablenames")
	public ResponseEntity<Object> getTableNames(HttpServletRequest request) {

		try {
			List<TableNameModel> tableNames = navegaBalancesService.getTableNamesQueryNavega();
			return responseBuilder.buildSuccessResponse(request, tableNames);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@GetMapping("/tabledata")
	public ResponseEntity<Object> getTableData(@RequestParam(value = "tableName", required = false) String tableName,
			HttpServletRequest request) {

		try {
			List<NavegaBalancesModel> models = this.navegaBalancesService.getTableDataQueryNavega(tableName);
			return responseBuilder.buildSuccessResponse(request, models);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}

	@GetMapping("/searchbyebsaccount/{ebsAccount}")
	public ResponseEntity<Object> getAllNavegaBalances(@PathVariable String ebsAccount, HttpServletRequest request) {

		try {
			NavegaBalancesModel navegaBalance = navegaBalancesService.getBalanceByEbsAccount(ebsAccount);
			return responseBuilder.buildSuccessResponse(request, navegaBalance);
		} catch (Exception ex) {
			return responseBuilder.buildErrorResponse(ex, request);
		}
	}
}
