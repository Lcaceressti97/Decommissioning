package hn.com.tigo.equipmentinsurance.services.interfaces;

import java.util.List;

import hn.com.tigo.equipmentinsurance.models.ConfigParametersModel;

public interface IConfigParametersService {

	List<ConfigParametersModel> getAll();

	List<ConfigParametersModel> getByIdApplication(Long idApplication);
	
	ConfigParametersModel getByName(String name);

}
