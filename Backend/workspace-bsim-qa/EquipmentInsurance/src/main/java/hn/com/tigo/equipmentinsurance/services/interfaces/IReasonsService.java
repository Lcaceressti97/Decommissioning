package hn.com.tigo.equipmentinsurance.services.interfaces;

import java.util.List;

import hn.com.tigo.equipmentinsurance.models.ReasonsModel;

public interface IReasonsService {

	List<ReasonsModel> getAllReasons();

	ReasonsModel getReasonById(Long id);

	void addReason(ReasonsModel model);

	void updateReason(Long id, ReasonsModel model);

	void deleteReason(Long id);
}
