package hn.com.tigo.equipmentaccessoriesbilling.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentaccessoriesbilling.entities.ControlUserPermissionsEntity;

@Repository
public interface IControlUserPermissionsRepository extends JpaRepository<ControlUserPermissionsEntity, Long> {

	ControlUserPermissionsEntity findByUserName(String userName);

	@Query(value = "SELECT * FROM MEA_CONTROL_USER_PERMISSIONS WHERE GENERATE_BILL = 'Y' OR ISSUE_SELLER_INVOICE = 'Y'", nativeQuery = true)
	List<ControlUserPermissionsEntity> getIssuingUsers();
}
