package hn.com.tigo.equipmentaccessoriesbilling.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import hn.com.tigo.equipmentaccessoriesbilling.entities.PremiumRatesEntity;

@Repository
public interface IPremiumRatesRepository extends JpaRepository<PremiumRatesEntity, Long> {

	@Query(value = "SELECT MONTHLY_PREMIUM FROM SE_PREMIUM_RATES WHERE MODEL = :model AND DEVICE_VALUE LIKE :deviceValue AND ROWNUM = 1", nativeQuery = true)
	Double getMonthlyPremiumByModel(@Param("model") String model, @Param("deviceValue") String deviceValue);
}
