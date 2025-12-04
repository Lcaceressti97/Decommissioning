package hn.com.tigo.equipmentinsurance.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentinsurance.entities.PriceMasterEntity;

@Repository
public interface IPriceMasterRepository extends JpaRepository<PriceMasterEntity, Long> {

	List<PriceMasterEntity> findByInventoryTypeAndModel(String inventoryType, String model);
	
	PriceMasterEntity findByModel(String model);
	
	

}
