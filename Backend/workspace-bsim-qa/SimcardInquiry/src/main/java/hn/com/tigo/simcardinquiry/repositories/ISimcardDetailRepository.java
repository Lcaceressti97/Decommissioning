package hn.com.tigo.simcardinquiry.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hn.com.tigo.simcardinquiry.entities.SimcardDetailEntity;

@Repository
public interface ISimcardDetailRepository extends JpaRepository<SimcardDetailEntity, Long> {

	@Query(value = "SELECT * FROM SIMCARD_DETAIL WHERE ID_ORDER_CONTROL = :idOrderControl", nativeQuery = true)
	List<SimcardDetailEntity> getSimcardDetailById(Long idOrderControl);

	@Transactional
	@Modifying
	@Query("UPDATE SimcardDetailEntity sd SET sd.status = 'R' WHERE sd.idOrderControl = :idOrderControl")
	void updateStatusByIdOrderControl(Long idOrderControl);

	SimcardDetailEntity getByIcc(String icc);

	@Query(value = "SELECT * FROM SIMCARD_DETAIL WHERE (:type = 1 AND IMSI = :value ) OR (:type = 2 AND ICC = :value )", nativeQuery = true)
	SimcardDetailEntity findByImsiOrIcc(int type, String value);

}
