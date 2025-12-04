package hn.com.tigo.jteller.services.interfaces;

import java.util.List;

import hn.com.tigo.jteller.entities.InvoiceDetailEntity;

public interface IInvoiceDetailService {

	List<InvoiceDetailEntity> getInvoiceDetailByIdPreInvoice(Long idPreInvoice);
	
}
