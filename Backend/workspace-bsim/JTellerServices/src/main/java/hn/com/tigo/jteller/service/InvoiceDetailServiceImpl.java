package hn.com.tigo.jteller.service;

import java.util.List;

import org.springframework.stereotype.Service;

import hn.com.tigo.jteller.entities.InvoiceDetailEntity;
import hn.com.tigo.jteller.respositories.IInvoiceDetailRepository;
import hn.com.tigo.jteller.services.interfaces.IInvoiceDetailService;

@Service
public class InvoiceDetailServiceImpl implements IInvoiceDetailService{

	// Props
	private IInvoiceDetailRepository invoiceDetailRepository; 
	
	// Constructor
	public InvoiceDetailServiceImpl(IInvoiceDetailRepository invoiceDetailRepository) {
		super();
		this.invoiceDetailRepository = invoiceDetailRepository;
	}
	
	
	/**
	 * Servicio que devuelve la lista de detalle para la factura en caso que haya detalles
	 * 
	 */
	@Override
	public List<InvoiceDetailEntity> getInvoiceDetailByIdPreInvoice(Long idPreInvoice) {
		
		List<InvoiceDetailEntity> list = this.invoiceDetailRepository.getInvoiceDetailByIdPrefecture(idPreInvoice);
		
		return list.isEmpty()== true ? null : list;
	}

}
