package hn.com.tigo.equipmentaccessoriesbilling.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentaccessoriesbilling.entities.BillingServicesEntity;

@Repository
public interface IBillingServicesRepository extends JpaRepository<BillingServicesEntity, Long> {

	@Query(value = "SELECT * FROM MEA_BILLING_SERVICES WHERE SERVICE_CODE = :serviceCode", nativeQuery = true)
	BillingServicesEntity findByServiceCode(Long serviceCode);
}
