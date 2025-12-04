package hn.com.tigo.selfconsumption.services.interfaces;

import java.util.List;

import hn.com.tigo.selfconsumption.models.ChangeCodeHistoricalModel;

public interface IChangeCodeHistoricalService {

	List<ChangeCodeHistoricalModel> getChangeCodeHistoricalById(Long chargeCodeId);
	
	void addChangeCodeHistorical(ChangeCodeHistoricalModel model);

	void updateChangeCodeHistorical(Long id, ChangeCodeHistoricalModel model);
}
 