package hn.com.tigo.jteller.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hn.com.tigo.jteller.dto.BillingDocument;
import hn.com.tigo.jteller.dto.BillingInfo;
import hn.com.tigo.jteller.dto.InfoCliente;
import hn.com.tigo.jteller.entities.BillingEntity;
import hn.com.tigo.jteller.provider.CustomerInfoProvider;
import hn.com.tigo.jteller.provider.InvoiceEnhancedProvider;
import hn.com.tigo.jteller.response.ClientInfoPreBillResponse;
import hn.com.tigo.jteller.response.ClientInfoResponse;
import hn.com.tigo.jteller.services.interfaces.IBillingService;
import hn.com.tigo.jteller.utils.ValidateParam;

@RestController
@RequestMapping(path = "/payment")
public class ClientInfoController {

	// Props
	private final CustomerInfoProvider customerInfoProvider;
	private final InvoiceEnhancedProvider invoiceEnhancedProvider;
	private final IBillingService billingService;

	public ClientInfoController(CustomerInfoProvider customerInfoProvider,
			InvoiceEnhancedProvider invoiceEnhancedProvider,
			IBillingService billingService) {
		
		this.customerInfoProvider = customerInfoProvider;
		this.invoiceEnhancedProvider = invoiceEnhancedProvider;
		this.billingService = billingService;
	}

	@PostConstruct
	void setGlobalSecurityContext() {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

	@GetMapping(path = "/getClientInfo/see")
	public ResponseEntity<?> getClientInfoByType(@RequestParam("type") String type,
			@RequestParam("value") String value) {

		// Validamos parámetros
		ValidateParam.type(type);
		ValidateParam.value(value);

		// Instanciamos la clase de retorno
		ClientInfoResponse clientInfoResponse = new ClientInfoResponse();

		final boolean validatePrimaryIdentity = type.equals("primaryIdentity");

		InfoCliente infoCliente = null;
		BillingInfo billingInfo = null;

		// Validamos que servicios consumir según el type
		if (!validatePrimaryIdentity) {

			infoCliente = this.customerInfoProvider.getCustomerInfoByAccountCode(value);

			billingInfo = this.invoiceEnhancedProvider.getInvoiceInfo(infoCliente.getAccountCode());

		} else {
			infoCliente = this.customerInfoProvider.getCustomerInfoByPrimaryIdentity(value);
			billingInfo = this.invoiceEnhancedProvider.getInvoiceInfo(infoCliente.getAccountCode());

		}

		clientInfoResponse.setBillingInfo(billingInfo);
		clientInfoResponse.setInfoCliente(infoCliente);
		clientInfoResponse.setResponseCode(0);
		clientInfoResponse.setResponseDescription("Operation Success");

		return ResponseEntity.ok(clientInfoResponse);
	}

	@GetMapping(path = "/getClientInfoPreBill/see")
	public ResponseEntity<?> getClientInfoPreBillByType(@RequestParam("type") String type,
			@RequestParam("value") String value) {

		// Validamos parámetros
		ValidateParam.typeBill(type);
		ValidateParam.value(value);

		// Instanciamos el response
		ClientInfoPreBillResponse clientInfoResponse = new ClientInfoPreBillResponse();
		InfoCliente infoCliente = new InfoCliente();

		
		// Instanciamos la lista de los detalles de la factura
		List<BillingDocument> billingDocuments = new ArrayList<>();
		
		// Instanciamos el objeto de los detalles de la factura
		BillingInfo billingInfo = new BillingInfo();
		
		// Consumir el repositorio para buscar la factura por el AcctCode
		BillingEntity billingEntity = this.billingService.getByIdAndStatus(Long.parseLong(value),1L);
		
		/**
		 * Condición que valida si existe la factura en la tabla, sino se estructura una respuesta sin data
		 * solo retorna la información del cliente
		 * 
		 */
		if(billingEntity!=null) {
			
			// Consumir el servicio para la información del cliente
			if(billingEntity.getAcctCode()==null) {
				infoCliente = this.customerInfoProvider.getCustomerInfoByPrimaryIdentity(billingEntity.getPrimaryIdentity());
				
			}else {
				infoCliente = this.customerInfoProvider.getCustomerInfoByAccountCode(billingEntity.getAcctCode());
				
			}
			
			// Seteamos los valores en caso que no exista la factura por el AcctCode
			BillingDocument billingDocument2 = new BillingDocument();
			billingDocument2.setExchangeRate(billingEntity.getExchangeRate().toString());
			billingDocument2.setInvoicePre(billingEntity.getId().toString());
			billingDocument2.setInvoiceAmount(billingEntity.getAmountTotal().toString());
			billingDocument2.setTaxAmount(billingEntity.getAmountTax().toString());
			billingDocument2.setTransType("PRE");
			
			billingDocuments.add(billingDocument2);
			
			billingInfo.setTotalDocuments(1);
			billingInfo.setTotalOpenAmount(billingEntity.getAmountTotal().toString());
			billingInfo.setCurrency("USD");
			billingInfo.setBillingDocuments(billingDocuments);
			
		}
		
		
		// Seteamos los valores para el response
		clientInfoResponse.setResponseCode(0);
		clientInfoResponse.setResponseDescription("Operation Success");
		clientInfoResponse.setInfoCliente(infoCliente);
		clientInfoResponse.setBillingInfo(billingInfo);

		return ResponseEntity.ok(clientInfoResponse);
	}

	@GetMapping(path = "/test")
	public ResponseEntity<?> test(@RequestParam("type") String type, @RequestParam("value") String value) {

		// Variable de response
		ClientInfoResponse clientInfoResponse = new ClientInfoResponse();

		final boolean validatePrimeryIdentity = type.equals("primaryIdentity");

		InfoCliente infoCliente = null;
		BillingInfo billingInfo = null;

		if (!validatePrimeryIdentity) {
			infoCliente = this.customerInfoProvider.getCustomerInfoByAccountCode(value);

			billingInfo = this.invoiceEnhancedProvider.getInvoiceInfo(infoCliente.getAccountCode());

		} else {
			infoCliente = this.customerInfoProvider.getCustomerInfoByPrimaryIdentity(value);
			billingInfo = this.invoiceEnhancedProvider.getInvoiceInfo(infoCliente.getAccountCode());
		}

		clientInfoResponse.setBillingInfo(billingInfo);
		clientInfoResponse.setInfoCliente(infoCliente);
		clientInfoResponse.setResponseCode(0);
		clientInfoResponse.setResponseDescription("Operation Success");

		return ResponseEntity.ok(clientInfoResponse);
	}

}
