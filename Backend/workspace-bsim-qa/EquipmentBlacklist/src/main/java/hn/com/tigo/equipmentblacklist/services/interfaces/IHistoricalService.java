package hn.com.tigo.equipmentblacklist.services.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import hn.com.tigo.equipmentblacklist.models.AddBloqueoImeiRequestModel;
import hn.com.tigo.equipmentblacklist.models.HistoricalModel;
import hn.com.tigo.equipmentblacklist.models.GeneralResponseImeiModel;
import hn.com.tigo.equipmentblacklist.models.RemoveBloqueoImeiRequestModel;

public interface IHistoricalService {

	Page<HistoricalModel> getAllHistoricalByPagination(Pageable pageable);

	HistoricalModel getById(Long id);

	List<HistoricalModel> findByWithType(int type, String value);

	GeneralResponseImeiModel addBloqueoImei(AddBloqueoImeiRequestModel request);

	GeneralResponseImeiModel removeBloqueoImei(RemoveBloqueoImeiRequestModel request);

	void add(HistoricalModel model);

	void update(Long id, HistoricalModel model);

	void delete(Long id);

}
