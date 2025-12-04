package hn.com.tigo.equipmentblacklist.services.interfaces;

import java.util.List;

import hn.com.tigo.equipmentblacklist.models.HistoricalDetailModel;

public interface IHistoricalDetailService {

	List<HistoricalDetailModel> getAll();
	
	List<HistoricalDetailModel> getHistoricalByEsnImei(String esnImei);

	HistoricalDetailModel getById(Long id);

	void delete(Long id);

}
