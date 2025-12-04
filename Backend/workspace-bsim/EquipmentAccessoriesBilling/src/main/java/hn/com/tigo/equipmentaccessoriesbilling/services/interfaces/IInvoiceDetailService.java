package hn.com.tigo.equipmentaccessoriesbilling.services.interfaces;

import java.util.List;

import hn.com.tigo.equipmentaccessoriesbilling.entities.InvoiceDetailEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.InvoiceDetailModel;

public interface IInvoiceDetailService {

	List<InvoiceDetailModel> getAll();

	List<InvoiceDetailModel> getDetailByIdInvoice(Long idInvoice);

	List<InvoiceDetailEntity> getDetailByIdInvoiceEntity(Long idInvoice);

	InvoiceDetailModel getById(Long id);

	InvoiceDetailModel getDetailBySerie(String serieOrBoxNumber);

	void add(InvoiceDetailModel model);

	void update(Long id, InvoiceDetailModel model);

	void delete(Long id);
}
