package hn.com.tigo.comodatos.services.interfaces;

import java.util.List;

import hn.com.tigo.comodatos.models.ConfigParametersModel;

public interface IConfigParametersService {

	List<ConfigParametersModel> getAll();

	List<ConfigParametersModel> getByIdApplication(Long idApplication);
	
	void update(Long id, ConfigParametersModel model);
	
}
