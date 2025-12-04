package hn.com.tigo.equipmentinsurance.services.interfaces;

import java.net.MalformedURLException;

import hn.com.tigo.equipmentinsurance.models.BillingModel;

public interface IBillingService {

	BillingModel getById(Long id);
	
	Long addInvoiceInsuranceClaim(BillingModel model) throws MalformedURLException;

}
