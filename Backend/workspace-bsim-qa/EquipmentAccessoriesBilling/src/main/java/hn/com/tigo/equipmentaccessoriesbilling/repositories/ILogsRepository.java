package hn.com.tigo.equipmentaccessoriesbilling.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentaccessoriesbilling.entities.LogsEntity;

@Repository
public interface ILogsRepository extends JpaRepository<LogsEntity, Long> {

    @Query(value = "SELECT * FROM MEA_LOGS WHERE TRUNC(CREATED) = TRUNC(SYSDATE) ORDER BY CREATED DESC", nativeQuery = true)
    Page<LogsEntity> getLogs(Pageable pageable);

    @Query(value = "SELECT * FROM MEA_LOGS WHERE CREATED >= :startDate AND CREATED < :endDate ORDER BY CREATED DESC", nativeQuery = true)
    Page<LogsEntity> getLogsByDateRange(Pageable pageable, LocalDate startDate, LocalDate endDate);

    Page<LogsEntity> getLogsByTypeError(Pageable pageable,long typeError);


}
