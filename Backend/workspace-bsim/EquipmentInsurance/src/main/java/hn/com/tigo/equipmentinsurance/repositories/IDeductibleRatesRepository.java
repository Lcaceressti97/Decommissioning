package hn.com.tigo.equipmentinsurance.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentinsurance.entities.DeductibleRatesEntity;
import hn.com.tigo.equipmentinsurance.entities.ReasonsEntity;

@Repository
public interface IDeductibleRatesRepository extends JpaRepository<DeductibleRatesEntity, Long> {

	List<DeductibleRatesEntity> getDeductibleRatesByModel(String model);
	
	DeductibleRatesEntity getDeductibleRatesByModelAndReason(String model, ReasonsEntity reason);
}
