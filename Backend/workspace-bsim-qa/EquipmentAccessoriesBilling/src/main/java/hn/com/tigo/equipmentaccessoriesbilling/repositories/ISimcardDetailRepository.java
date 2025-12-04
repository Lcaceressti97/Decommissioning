package hn.com.tigo.equipmentaccessoriesbilling.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentaccessoriesbilling.entities.SimcardDetailEntity;

@Repository
public interface ISimcardDetailRepository extends JpaRepository<SimcardDetailEntity, Long> {

	@Query(value = "SELECT * FROM SIMCARD_DETAIL WHERE (:type = 1 AND IMSI = :value ) OR (:type = 2 AND ICC = :value )", nativeQuery = true)
	SimcardDetailEntity findByImsiOrIcc(int type, String value);
}
