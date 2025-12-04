package hn.com.tigo.equipmentaccessoriesbilling.repositories;

import hn.com.tigo.equipmentaccessoriesbilling.entities.LogsServicesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILogsServiceRepository extends JpaRepository<LogsServicesEntity, Long> {
}
