package hn.com.tigo.equipmentinsurance.services.interfaces;

import hn.com.tigo.equipmentinsurance.models.BillingModel;

public interface IBillingService {

	BillingModel getById(Long id);
	
	Long addInvoiceInsuranceClaim(BillingModel model);

}
