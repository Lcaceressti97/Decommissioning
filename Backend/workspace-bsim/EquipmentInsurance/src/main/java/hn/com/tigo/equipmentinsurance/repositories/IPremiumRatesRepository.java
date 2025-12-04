package hn.com.tigo.equipmentinsurance.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentinsurance.entities.PremiumRatesEntity;

@Repository
public interface IPremiumRatesRepository extends JpaRepository<PremiumRatesEntity, Long> {

	List<PremiumRatesEntity> getPremiumRatesByModel(String model);
}
