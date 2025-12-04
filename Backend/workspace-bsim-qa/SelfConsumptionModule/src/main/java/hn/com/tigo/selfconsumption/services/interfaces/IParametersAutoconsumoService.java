package hn.com.tigo.selfconsumption.services.interfaces;

import java.util.List;

import hn.com.tigo.selfconsumption.models.ParametersAutoconsumoModel;

public interface IParametersAutoconsumoService {

	List<ParametersAutoconsumoModel> getAllParametersAutoconsumo();

	List<ParametersAutoconsumoModel> getByIdApplication(Long idApplication);

	void updateParametersAutoconsumo(Long id, ParametersAutoconsumoModel model);
	

}
