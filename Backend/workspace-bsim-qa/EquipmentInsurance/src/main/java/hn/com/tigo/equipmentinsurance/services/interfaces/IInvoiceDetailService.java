package hn.com.tigo.equipmentinsurance.services.interfaces;

import java.util.List;

import hn.com.tigo.equipmentinsurance.entities.InvoiceDetailEntity;
import hn.com.tigo.equipmentinsurance.models.InvoiceDetailModel;

public interface IInvoiceDetailService {

	List<InvoiceDetailEntity> getDetailByIdInvoiceEntity(Long idInvoice);

	void add(InvoiceDetailModel model);
}
