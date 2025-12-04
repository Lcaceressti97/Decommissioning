package hn.com.tigo.comodatos.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.comodatos.models.AccountCBS;
import hn.com.tigo.comodatos.provider.CustomerInfoProvider;
import hn.com.tigo.comodatos.provider.SiebelQueryCustomerProvider;
import hn.com.tigo.comodatos.utils.ResponseBuilder;
import hn.com.tigo.comodatos.utils.Util;

@RestController
@RequestMapping(path = "/customer/info")
public class CustomerInfoController {

	// Props
	private final CustomerInfoProvider customerInfoProvider;
	private final SiebelQueryCustomerProvider siebelQueryCustomerProvider;
	private final Util util;
	private final ResponseBuilder responseBuilder;
	
	public CustomerInfoController(CustomerInfoProvider customerInfoProvider, SiebelQueryCustomerProvider siebelQueryCustomerProvider) {
		this.customerInfoProvider = customerInfoProvider;
		this.siebelQueryCustomerProvider = siebelQueryCustomerProvider;
		this.util = new Util();
		this.responseBuilder = new ResponseBuilder();
	}
	
	@GetMapping("/subscriber/{subscriber}")
	public ResponseEntity<Object> getAllInvoicesByPagination(@PathVariable String subscriber, HttpServletRequest request) {
		
		try {
			AccountCBS data = this.customerInfoProvider.getCustomerInfoByPrimaryIdentity(subscriber);
			
			data.setServiceAccount(this.siebelQueryCustomerProvider.getServiceAccount(data.getBillingAccount()));
			
			return this.responseBuilder.buildSuccessResponse(request, data);
		}catch(Exception ex) {
			return this.responseBuilder.buildErrorResponse(ex, request);
			
		}

		
	}
}
