package hn.com.tigo.equipmentaccessoriesbilling.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentaccessoriesbilling.entities.PriceMasterEntity;

@Repository
public interface IPriceMasterRepository extends JpaRepository<PriceMasterEntity, Long> {

	List<PriceMasterEntity> getPriceMasterByModel(String model);
	
	PriceMasterEntity getPriceMasterByModelAndInventoryType(String model, String inventoryType);

}
