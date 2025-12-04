package hn.com.tigo.equipmentaccessoriesbilling.services.interfaces;

import java.util.List;

import hn.com.tigo.equipmentaccessoriesbilling.models.ConfigParametersModel;

public interface IConfigParametersService {

	List<ConfigParametersModel> getAll();

	List<ConfigParametersModel> getByIdApplication(Long idApplication);
	
	ConfigParametersModel getByName(String name);

}
