package hn.com.tigo.equipmentinsurance.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentinsurance.entities.HistoricalDetailEntity;


@Repository
public interface IHistoricalDetailRepository extends JpaRepository<HistoricalDetailEntity, Long> {

	@Query(value = "SELECT * FROM BL_HISTORICAL_DETAIL WHERE ESN_IMEI =:esnImei ORDER BY CREATED_DATE DESC", nativeQuery = true)
	List<HistoricalDetailEntity> getHistoricalByEsnImei(String esnImei);
}
