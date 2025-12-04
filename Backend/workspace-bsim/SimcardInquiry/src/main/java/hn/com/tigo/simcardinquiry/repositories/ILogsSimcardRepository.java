package hn.com.tigo.simcardinquiry.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hn.com.tigo.simcardinquiry.entities.LogsSimcardEntity;

@Repository
public interface ILogsSimcardRepository extends JpaRepository<LogsSimcardEntity, Long> {

	@Query(value = "SELECT * FROM SIMCARD_LOGS WHERE CREATED >= :startDate AND CREATED < :endDate ORDER BY CREATED DESC", nativeQuery = true)
	List<LogsSimcardEntity> getLogsByDateRange(LocalDate startDate, LocalDate endDate);

	List<LogsSimcardEntity> getLogsByTypeError(long typeError);
}
