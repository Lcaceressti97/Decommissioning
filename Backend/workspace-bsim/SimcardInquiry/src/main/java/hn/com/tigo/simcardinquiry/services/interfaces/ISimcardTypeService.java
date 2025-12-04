package hn.com.tigo.simcardinquiry.services.interfaces;

import java.util.List;

import hn.com.tigo.simcardinquiry.models.SimcardTypeModel;

public interface ISimcardTypeService {

	List<SimcardTypeModel> getAll();

	SimcardTypeModel getById(Long id);

	void createType(SimcardTypeModel model);

	void updateType(Long id, SimcardTypeModel model);

	void deleteType(Long id);
}
