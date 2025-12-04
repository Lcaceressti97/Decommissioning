package hn.com.tigo.simcardinquiry.services.interfaces;

import java.util.List;

import hn.com.tigo.simcardinquiry.models.SimcardSuppliersModel;

public interface ISimcardSuppliersService {

	List<SimcardSuppliersModel> getAll();

	SimcardSuppliersModel getById(Long id);

	void createSupplier(SimcardSuppliersModel model);

	void updateSupplier(Long id, SimcardSuppliersModel model);

	void deleteSupplier(Long id);

}
