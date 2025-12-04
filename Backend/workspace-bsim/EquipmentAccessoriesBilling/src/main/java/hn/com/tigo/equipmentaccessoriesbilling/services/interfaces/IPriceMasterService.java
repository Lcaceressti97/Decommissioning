package hn.com.tigo.equipmentaccessoriesbilling.services.interfaces;

import java.util.List;

import hn.com.tigo.equipmentaccessoriesbilling.models.PriceMasterDTO;
import hn.com.tigo.equipmentaccessoriesbilling.models.PriceMasterModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPriceMasterService {

	Page<PriceMasterModel> getAll(Pageable pageable);

	List<PriceMasterDTO> getPriceMasterByModel(String model);

	List<PriceMasterModel> getPriceMasterModelByModel(String model);

	List<PriceMasterDTO> getAllPriceMaster();

	PriceMasterModel getPriceMasterById(Long id);

	PriceMasterModel getPriceMasterByModelAndInventoryType(String model, String inventoryType);

	void addPriceMaster(PriceMasterModel model);

	void updatePriceMaster(Long id, PriceMasterModel model);

	void deletePriceMaster(Long id);

}
