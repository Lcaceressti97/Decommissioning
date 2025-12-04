package hn.com.tigo.simcardinquiry.services.interfaces;

import java.util.List;

import hn.com.tigo.simcardinquiry.models.SimcardCustomerModel;

public interface ISimcardCustomerService {

	List<SimcardCustomerModel> getAll();

	SimcardCustomerModel getById(Long id);

	void createCustomer(SimcardCustomerModel model);

	void updateCustomer(Long id, SimcardCustomerModel model);

	void deleteCustomer(Long id);
}
