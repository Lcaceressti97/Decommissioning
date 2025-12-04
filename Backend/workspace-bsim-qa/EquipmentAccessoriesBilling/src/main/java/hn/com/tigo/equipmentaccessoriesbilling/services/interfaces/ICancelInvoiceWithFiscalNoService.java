package hn.com.tigo.equipmentaccessoriesbilling.services.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import hn.com.tigo.equipmentaccessoriesbilling.models.CancelInvoiceWithFiscalNoModel;

public interface ICancelInvoiceWithFiscalNoService {

	Page<CancelInvoiceWithFiscalNoModel> getAll(Pageable pageable);

	CancelInvoiceWithFiscalNoModel getById(Long id);

	List<CancelInvoiceWithFiscalNoModel> getByUserName(String userName);

	void add(CancelInvoiceWithFiscalNoModel model);

	void update(Long id, CancelInvoiceWithFiscalNoModel model);

	void delete(Long id);
}
