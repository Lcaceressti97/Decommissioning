package hn.com.tigo.simcardinquiry.services.interfaces;

import java.util.List;

import hn.com.tigo.simcardinquiry.models.SimcardModelModel;

public interface ISimcardModelService {

	List<SimcardModelModel> getAll();

	SimcardModelModel getById(Long id);

	void createModel(SimcardModelModel model);

	void updateModel(Long id, SimcardModelModel model);

	void deleteModel(Long id);
}
