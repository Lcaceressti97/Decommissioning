package hn.com.tigo.equipmentaccessoriesbilling.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentaccessoriesbilling.entities.EquipmentInsuranceControlEntity;


@Repository
public interface IEquipmentInsuranceControlRepository extends JpaRepository<EquipmentInsuranceControlEntity, Long> {

	@Query(value = "SELECT DISTINCT * FROM SE_EQUIPMENT_INSURANCE_CONTROL WHERE ESN =:esn AND TRANSACTION_CODE = 'SEG61'", nativeQuery = true)
	List<EquipmentInsuranceControlEntity> getEquipmentInsuranceControlByEsn(String esn);

	boolean existsByEsnAndTransactionCode(String esn, String transactionCode);

}
