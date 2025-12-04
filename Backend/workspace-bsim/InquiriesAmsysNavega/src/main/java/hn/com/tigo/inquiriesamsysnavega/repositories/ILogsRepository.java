package hn.com.tigo.inquiriesamsysnavega.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hn.com.tigo.inquiriesamsysnavega.entities.LogsEntity;

@Repository
public interface ILogsRepository extends JpaRepository<LogsEntity, Long> {

	@Query(value = "SELECT * FROM AN_LOGS WHERE CREATED >= :startDate AND CREATED < :endDate ORDER BY CREATED DESC", nativeQuery = true)
	List<LogsEntity> getLogsByDateRange(LocalDate startDate, LocalDate endDate);

	List<LogsEntity> getLogsByTypeError(long typeError);
}
