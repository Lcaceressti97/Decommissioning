package hn.com.tigo.equipmentblacklist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import hn.com.tigo.equipmentblacklist.entities.ImeiControlFileEntity;

@Repository
public interface ImeiControlFileRepository extends JpaRepository<ImeiControlFileEntity, Long> {

	@Query(value = "SELECT * FROM (SELECT * FROM BL_IMEI_CONTROL_FILE WHERE STATUS = 1 ORDER BY CREATED_DATE DESC) WHERE ROWNUM < 100", nativeQuery = true)
	List<ImeiControlFileEntity> getAllByStatusActive();

	@Query(value = "SELECT * FROM BL_IMEI_CONTROL_FILE WHERE PHONE = :phone ORDER BY STATUS DESC, CREATED_DATE DESC", nativeQuery = true)
	List<ImeiControlFileEntity> getByPhoneAndStatus(String phone);

	@Query(value = "SELECT * FROM BL_IMEI_CONTROL_FILE WHERE (:type = 1 AND PHONE = :value AND STATUS = 1 ) OR (:type = 2 AND IMEI = :value AND STATUS = 1 ) ORDER BY CREATED_DATE DESC", nativeQuery = true)
	List<ImeiControlFileEntity> findByPhoneOrImeiWithType(int type, String value);

    ImeiControlFileEntity findByPhoneAndStatus(String phone, Long status);
}
