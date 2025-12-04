package hn.com.tigo.comodatos.services.interfaces;

import java.util.List;

import hn.com.tigo.comodatos.models.CancelMooringModel;
import hn.com.tigo.comodatos.models.ControlCancellationModel;

public interface IControlCancellationService {

	List<ControlCancellationModel> getAll();
	
	void add(ControlCancellationModel model);
	
	CancelMooringModel cancel(CancelMooringModel model);
}
