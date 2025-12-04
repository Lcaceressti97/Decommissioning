package hn.com.tigo.equipmentaccessoriesbilling.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentaccessoriesbilling.entities.InsuranceRatesEntity;


@Repository
public interface IInsuranceRatesRepository extends JpaRepository<InsuranceRatesEntity, Long> {

	@Query(value = "SELECT MONTHLY_FEE FROM SE_INSURANCE_RATES_MODEL WHERE MODEL = :model AND :deviceValue BETWEEN VALUE_FROM AND VALUE_UP AND PERIOD_NUMBER = 1 AND ROWNUM = 1", nativeQuery = true)
	Double getMonthlyPremiumByModel(@Param("model") String model, @Param("deviceValue") Double deviceValue);
}
