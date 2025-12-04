package hn.com.tigo.equipmentinsurance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentinsurance.entities.BillingServicesEntity;

@Repository
public interface IBillingServicesRepository extends JpaRepository<BillingServicesEntity, Long> {

	@Query(value = "SELECT * FROM MEA_BILLING_SERVICES WHERE SERVICE_CODE = :serviceCode", nativeQuery = true)
	BillingServicesEntity findByServiceCode(Long serviceCode);
}
