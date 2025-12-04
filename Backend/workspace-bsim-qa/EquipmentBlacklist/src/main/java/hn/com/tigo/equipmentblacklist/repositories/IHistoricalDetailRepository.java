package hn.com.tigo.equipmentblacklist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

import hn.com.tigo.equipmentblacklist.entities.HistoricalDetailEntity;

@Repository
public interface IHistoricalDetailRepository extends JpaRepository<HistoricalDetailEntity, Long> {

	@Query(value = "SELECT * FROM BL_HISTORICAL_DETAIL WHERE ESN_IMEI =:esnImei ORDER BY CREATED_DATE DESC", nativeQuery = true)
	List<HistoricalDetailEntity> getHistoricalByEsnImei(String esnImei);

	@Query("SELECT h FROM HistoricalDetailEntity h WHERE h.idHistorical = :idHistorical AND h.status = :status")
	HistoricalDetailEntity findByIdHistoricalAndStatus(Long idHistorical, Long status);

}
