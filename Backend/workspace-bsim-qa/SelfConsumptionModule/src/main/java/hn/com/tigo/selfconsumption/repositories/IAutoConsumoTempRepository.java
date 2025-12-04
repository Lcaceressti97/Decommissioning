package hn.com.tigo.selfconsumption.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hn.com.tigo.selfconsumption.entities.AutoConsumoTempEntity;

@Repository
public interface IAutoConsumoTempRepository extends JpaRepository<AutoConsumoTempEntity, String> {

	@Query(value = "SELECT * FROM AUTO_CONSUMO_TEMP WHERE (TRUNC(CREATED) = TRUNC(SYSDATE) AND (:type = 1 AND FEE_ITEM_AMOUNT = :value) OR (:type = 2 AND PRI_IDENT_OF_SUBSC = :value) OR (:type = 3 AND CYCLE = :value))", nativeQuery = true)
	List<AutoConsumoTempEntity> findByFilter(int type, String value);

	@Query(value = "SELECT * FROM AUTO_CONSUMO_TEMP WHERE CREATED >= :startDate AND CREATED <= :endDate ORDER BY CREATED DESC", nativeQuery = true)
	List<AutoConsumoTempEntity> getAutoConsumoTempByDateRange(LocalDate startDate, LocalDate endDate);

}
