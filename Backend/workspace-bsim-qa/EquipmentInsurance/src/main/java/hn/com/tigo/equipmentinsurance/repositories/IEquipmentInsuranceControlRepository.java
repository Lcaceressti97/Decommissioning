package hn.com.tigo.equipmentinsurance.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentinsurance.entities.EquipmentInsuranceControlEntity;

@Repository
public interface IEquipmentInsuranceControlRepository extends JpaRepository<EquipmentInsuranceControlEntity, Long> {

    @Query(value = "SELECT * FROM SE_EQUIPMENT_INSURANCE_CONTROL ORDER BY ID DESC", nativeQuery = true)
    Page<EquipmentInsuranceControlEntity> getEquipmentInsuranceControlPaginated(Pageable pageable);

    List<EquipmentInsuranceControlEntity> getEquipmentInsuranceControlByPhoneNumber(String phoneNumber);

    @Query(value = "SELECT * FROM SE_EQUIPMENT_INSURANCE_CONTROL WHERE ESN =:esn ORDER BY ID DESC", nativeQuery = true)
    List<EquipmentInsuranceControlEntity> getEquipmentInsuranceControlByEsn(String esn);

    @Query(value = "SELECT * FROM (SELECT * FROM SE_EQUIPMENT_INSURANCE_CONTROL WHERE EQUIPMENT_MODEL = :equipmentModel AND ESN = :esn ORDER BY ID DESC) WHERE ROWNUM = 1", nativeQuery = true)
    Optional<EquipmentInsuranceControlEntity> getByEquipmentModelAndEsn(String equipmentModel, String esn);
}
