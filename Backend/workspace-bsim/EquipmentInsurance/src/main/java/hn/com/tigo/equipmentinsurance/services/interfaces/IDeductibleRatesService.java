package hn.com.tigo.equipmentinsurance.services.interfaces;

import java.util.List;

import hn.com.tigo.equipmentinsurance.models.CalculateDeductibleRequest;
import hn.com.tigo.equipmentinsurance.models.CalculateDeductibleResponse;
import hn.com.tigo.equipmentinsurance.models.DeductibleRatesModel;

public interface IDeductibleRatesService {

	List<DeductibleRatesModel> getAllDeductibleRates();

	List<DeductibleRatesModel> getDeductibleRatesByModel(String model);
	
	CalculateDeductibleResponse getCalculateDeductible(CalculateDeductibleRequest request);

	DeductibleRatesModel getDeductibleRatesById(Long id);

	void addDeductibleRates(DeductibleRatesModel model);

	void updateDeductibleRates(Long id, DeductibleRatesModel model);

	void deleteDeductibleRates(Long id);
}
