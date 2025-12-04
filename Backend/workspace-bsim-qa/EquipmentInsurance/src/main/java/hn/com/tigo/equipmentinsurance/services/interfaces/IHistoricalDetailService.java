package hn.com.tigo.equipmentinsurance.services.interfaces;

import java.util.List;

import hn.com.tigo.equipmentinsurance.models.HistoricalDetailModel;

public interface IHistoricalDetailService {

	List<HistoricalDetailModel> getHistoricalByEsnImei(String esnImei);

}
