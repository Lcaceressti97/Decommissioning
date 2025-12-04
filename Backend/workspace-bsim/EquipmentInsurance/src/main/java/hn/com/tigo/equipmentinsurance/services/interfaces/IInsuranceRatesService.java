package hn.com.tigo.equipmentinsurance.services.interfaces;

import java.util.List;

import hn.com.tigo.equipmentinsurance.models.ConsultationResponse;
import hn.com.tigo.equipmentinsurance.models.InsuranceRatesModel;

public interface IInsuranceRatesService {

	List<InsuranceRatesModel> getAll();
	
	List<ConsultationResponse> getMonthlyFeesByModel(String model);
	
	InsuranceRatesModel getById(Long id);

	void add(InsuranceRatesModel model);

	void update(Long id, InsuranceRatesModel model);

	void delete(Long id);
}
