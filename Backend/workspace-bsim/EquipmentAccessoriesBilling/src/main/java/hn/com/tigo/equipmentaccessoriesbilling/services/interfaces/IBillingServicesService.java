package hn.com.tigo.equipmentaccessoriesbilling.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import hn.com.tigo.equipmentaccessoriesbilling.models.BillingServicesModel;

public interface IBillingServicesService {

	Page<BillingServicesModel> getAllPageable(Pageable pageable);

	BillingServicesModel getById(Long id);

	void add(BillingServicesModel model);

	void update(Long id, BillingServicesModel model);

	void delete(Long id);
}
