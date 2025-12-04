package hn.com.tigo.selfconsumption.services.interfaces;

import java.util.List;

import hn.com.tigo.selfconsumption.models.ParamAutoconsumoHistModel;

public interface IParamAutoconsumoHistService {

	List<ParamAutoconsumoHistModel> getAllParamAutoconsumoHist(Long idParameter);

	void addParamAutoconsumoHist(ParamAutoconsumoHistModel model);
}
