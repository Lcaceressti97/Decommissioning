package hn.com.tigo.simcardinquiry.services.interfaces;

import java.util.List;

import hn.com.tigo.simcardinquiry.models.SimcardArtModel;

public interface ISimcardArtService {

	List<SimcardArtModel> getAll();

	SimcardArtModel getById(Long id);

	void createArt(SimcardArtModel model);

	void updateArt(Long id, SimcardArtModel model);

	void deleteArt(Long id);
}
