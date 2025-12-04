package hn.com.tigo.equipmentblacklist.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentblacklist.entities.HistoricalEntity;

@Repository
public interface IHistoricalRepository extends JpaRepository<HistoricalEntity, Long> {

	@Query(value = "SELECT *  FROM BL_HISTORICAL WHERE (:type = 1 AND PHONE = :value) OR (:type = 2 AND ESN = :value)", nativeQuery = true)
	List<HistoricalEntity> findByType(int type, String value);

	@Query(value = "SELECT COUNT(1) FROM BL_HISTORICAL WHERE ESN = :esn AND STATUS = :status", nativeQuery = true)
	int countByEsnAndStatus(String esn, Long status);

	boolean existsByEsnAndStatus(String esn, Long status);

	Optional<HistoricalEntity> findByEsn(String esn);

	HistoricalEntity findByEsnAndStatus(String esn, Long status);

	@Query("SELECT COALESCE(MAX(soc.id), 0) FROM HistoricalEntity soc WHERE soc.esn = :esn ")
	Long findLastInsertedId(String esn);

}
