package hn.com.tigo.equipmentaccessoriesbilling.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentaccessoriesbilling.entities.GenerationReportInvEntity;

@Repository
public interface IGenerationReportInvRepository extends JpaRepository<GenerationReportInvEntity, Long> {

}
