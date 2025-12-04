package hn.com.tigo.equipmentinsurance.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentinsurance.entities.SafeControlEquipmentEntity;

@Repository
public interface ISafeControlEquipmentRepository extends JpaRepository<SafeControlEquipmentEntity, Long> {

	@Query(value = "SELECT * FROM SE_SAFE_CONTROL_EQUIPMENT WHERE PHONE = :phone", nativeQuery = true)
	List<SafeControlEquipmentEntity> getByPhone(String phone);

}
