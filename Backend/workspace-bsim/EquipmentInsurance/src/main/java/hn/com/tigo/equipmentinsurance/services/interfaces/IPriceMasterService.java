package hn.com.tigo.equipmentinsurance.services.interfaces;

import java.util.List;

import hn.com.tigo.equipmentinsurance.models.PriceMasterModel;

public interface IPriceMasterService {

	List<PriceMasterModel> getPriceMaster();
	
	List<PriceMasterModel> findByInventoryTypeAndModel(String inventoryType, String model);

	PriceMasterModel getPriceMasterById(Long id);
	
	void createdPriceMaster(PriceMasterModel model);
	
	void updatePriceMaster(Long id, PriceMasterModel model);
	
	void deletePriceMaster(Long id);
}
