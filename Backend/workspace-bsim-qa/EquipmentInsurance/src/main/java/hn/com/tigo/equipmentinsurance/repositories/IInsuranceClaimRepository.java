package hn.com.tigo.equipmentinsurance.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentinsurance.entities.InsuranceClaimEntity;

@Repository
public interface IInsuranceClaimRepository extends JpaRepository<InsuranceClaimEntity, Long> {

	@Query(value = "SELECT * FROM SE_INSURANCE_CLAIM ORDER BY ID DESC", nativeQuery = true)
	Page<InsuranceClaimEntity> getInsuranceClaimPaginated(Pageable pageable);

	List<InsuranceClaimEntity> getInsuranceClaimByPhone(String phone);

	List<InsuranceClaimEntity> getInsuranceClaimByActualEsn(String actualEsn);
}
