package hn.com.tigo.simcardinquiry.services.interfaces;

import java.util.List;

import hn.com.tigo.simcardinquiry.models.SimcardGraphicModel;

public interface ISimcardGraphicService {

	List<SimcardGraphicModel> getAll();

	SimcardGraphicModel getById(Long id);

	void createGraphic(SimcardGraphicModel model);

	void updateGraphic(Long id, SimcardGraphicModel model);

	void deleteGraphic(Long id);
}
