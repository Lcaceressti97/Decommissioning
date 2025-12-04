package hn.com.tigo.comodatos.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.comodatos.provider.SiebelQueryCustomerProvider;
import hn.com.tigo.comodatos.utils.Util;

@RestController
@RequestMapping(path = "/siebel/customer")
public class SiebelCustomerController {

	// Props
	private final SiebelQueryCustomerProvider siebelQueryCustomerProvider;
	private final Util util;
	
	public SiebelCustomerController(SiebelQueryCustomerProvider siebelQueryCustomerProvider) {
		this.siebelQueryCustomerProvider = siebelQueryCustomerProvider;
		this.util = new Util();
		
	}
	
	@GetMapping("/integrationid/{integrationId}")
	public ResponseEntity<Object> getAllInvoicesByPagination(@PathVariable String integrationId) {
		this.siebelQueryCustomerProvider.getServiceAccount(integrationId);
		return ResponseEntity.ok(this.util.setSuccessWithoutData());
	}
	
}
