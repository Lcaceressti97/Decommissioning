package hn.com.tigo.simcardinquiry.services.interfaces;

import java.util.List;

import hn.com.tigo.simcardinquiry.models.ConfigParameterModel;

public interface IConfigParameterService {

	List<ConfigParameterModel> getByIdApplication(Long idApplication);

	void updateParameter(String parameterName, ConfigParameterModel model);

	ConfigParameterModel findByParameterName(String parameterName);

}
