package hn.com.tigo.equipmentaccessoriesbilling.services.interfaces;

import java.util.List;

import hn.com.tigo.equipmentaccessoriesbilling.models.InvoiceEquipmentAccessoriesModel;

public interface InvoiceEquipmentAccessoriesService {

	List<InvoiceEquipmentAccessoriesModel> getAll();

	List<InvoiceEquipmentAccessoriesModel> findBy(int type, String value);

	//List<InvoiceEquipmentAccessoriesModel> filterInvoices(Long invoiceStatus, String warehouse, String agency,
	//		String createdBy);

	List<InvoiceEquipmentAccessoriesModel> getExoneratedInvoices();
	
	 List<InvoiceEquipmentAccessoriesModel> getInvoicesByUserAndPermissions(String approvedUser);
	
	InvoiceEquipmentAccessoriesModel getById(Long id);

	void add(InvoiceEquipmentAccessoriesModel model);

	void update(Long id, InvoiceEquipmentAccessoriesModel model);

	void updateDocumentNo(Long id, String documentNo);

	void updateCorporateClient(Long id, String correlativeOrdenExemptNo, String correlativeCertificateExoNo);

	void updateSingleClient(Long id, String diplomaticCardNo);

	void updateStatusExoTax(Long id);
	
	void delete(Long id);

}
