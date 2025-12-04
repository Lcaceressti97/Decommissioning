package hn.com.tigo.jteller.service;

import java.util.List;

import org.springframework.stereotype.Service;

import hn.com.tigo.jteller.dto.BillingDocument;
import hn.com.tigo.jteller.dto.InfoCliente;
import hn.com.tigo.jteller.provider.CustomerInfoProvider;
import hn.com.tigo.jteller.services.interfaces.ICustomInfoService;

@Service
public class CustomInfoServiceImple implements ICustomInfoService{

	
	// Props
	private final CustomerInfoProvider customerInfoProvider;
	
	public CustomInfoServiceImple(CustomerInfoProvider customerInfoProvider) {
		this.customerInfoProvider = customerInfoProvider;
	}
	
	@Override
	public InfoCliente getInfoCliente() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BillingDocument> getBillingDocuments() {
		// TODO Auto-generated method stub
		return null;
	}

}
