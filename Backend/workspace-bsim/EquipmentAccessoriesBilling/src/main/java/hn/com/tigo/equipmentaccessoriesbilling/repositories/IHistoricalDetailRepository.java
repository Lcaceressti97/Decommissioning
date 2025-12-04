package hn.com.tigo.equipmentaccessoriesbilling.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentaccessoriesbilling.entities.HistoricalDetailEntity;


@Repository
public interface IHistoricalDetailRepository extends JpaRepository<HistoricalDetailEntity, Long> {

	@Query(value = "SELECT * FROM BL_HISTORICAL_DETAIL WHERE PHONE =:phone AND STATUS = 1 ORDER BY CREATED_DATE DESC", nativeQuery = true)
	List<HistoricalDetailEntity> getHistoricalDetailByPhone(String phone);

}
