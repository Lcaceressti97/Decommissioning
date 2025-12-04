package hn.com.tigo.jteller.services.interfaces;

import java.util.List;

import hn.com.tigo.jteller.dto.BillingDocument;
import hn.com.tigo.jteller.dto.InfoCliente;

public interface ICustomInfoService {

	InfoCliente getInfoCliente();
	
	List<BillingDocument> getBillingDocuments();
}
