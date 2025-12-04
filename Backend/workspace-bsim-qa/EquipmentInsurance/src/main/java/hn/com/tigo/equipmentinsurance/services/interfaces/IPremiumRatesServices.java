package hn.com.tigo.equipmentinsurance.services.interfaces;

import java.util.List;

import hn.com.tigo.equipmentinsurance.models.PremiumRatesModel;

public interface IPremiumRatesServices {

	List<PremiumRatesModel> getAll();

	List<PremiumRatesModel> getPremiumRatesByModel(String model);

	PremiumRatesModel getPremiumRatesById(Long id);

	void addPremiumRates(PremiumRatesModel model);

	void updatePremiumRates(Long id, PremiumRatesModel model);

	void deletePremiumRates(Long id);
}
