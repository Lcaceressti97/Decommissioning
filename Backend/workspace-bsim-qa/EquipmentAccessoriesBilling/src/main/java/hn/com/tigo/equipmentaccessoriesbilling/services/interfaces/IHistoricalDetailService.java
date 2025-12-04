package hn.com.tigo.equipmentaccessoriesbilling.services.interfaces;

import java.util.List;

import hn.com.tigo.equipmentaccessoriesbilling.models.HistoricalDetailModel;

public interface IHistoricalDetailService {

	List<HistoricalDetailModel> getHistoricalDetailByPhone(String phone);

}
