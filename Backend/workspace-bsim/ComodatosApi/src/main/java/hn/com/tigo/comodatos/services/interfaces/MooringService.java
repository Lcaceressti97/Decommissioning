package hn.com.tigo.comodatos.services.interfaces;

import java.util.List;

import hn.com.tigo.comodatos.models.MooringInfoModel;
import hn.com.tigo.comodatos.models.MooringModel;

public interface MooringService {

	MooringModel findById(Long id);
	
	List<MooringModel> findByIdMooringBilling(Long idMooringBilling);
	
	MooringInfoModel getInfoMooringBySubscriber(String subscriber);
	
	void add(Long id, MooringModel model);
	
	void validateSubcriber(String subcriber);
	
	void addTest(Long id, List<MooringModel> model);
	
	void update(Long id, MooringModel model);
	
	void delete(Long id, int status);

}
