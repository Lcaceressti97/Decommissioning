package hn.com.tigo.simcardinquiry.services.interfaces;

import java.util.List;

import hn.com.tigo.simcardinquiry.models.SimcardVersionModel;

public interface ISimcardVersionService {

	List<SimcardVersionModel> getAll();

	List<SimcardVersionModel> getVersionByIdModel(Long idModel);

	SimcardVersionModel getById(Long id);

	void createVersion(SimcardVersionModel model);

	void updateVersion(Long id, SimcardVersionModel model);

	void deleteVersion(Long id);
}
