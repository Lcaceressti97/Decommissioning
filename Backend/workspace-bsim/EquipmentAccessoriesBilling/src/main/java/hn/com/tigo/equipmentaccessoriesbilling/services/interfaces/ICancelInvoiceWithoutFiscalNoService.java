package hn.com.tigo.equipmentaccessoriesbilling.services.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import hn.com.tigo.equipmentaccessoriesbilling.models.CancelInvoiceWithoutFiscalNoModel;

public interface ICancelInvoiceWithoutFiscalNoService {

	Page<CancelInvoiceWithoutFiscalNoModel> getAll(Pageable pageable);

	CancelInvoiceWithoutFiscalNoModel getById(Long id);

	List<CancelInvoiceWithoutFiscalNoModel> getByUserName(String userName);

	void add(CancelInvoiceWithoutFiscalNoModel model);

	void update(Long id, CancelInvoiceWithoutFiscalNoModel model);

	void delete(Long id);
}
