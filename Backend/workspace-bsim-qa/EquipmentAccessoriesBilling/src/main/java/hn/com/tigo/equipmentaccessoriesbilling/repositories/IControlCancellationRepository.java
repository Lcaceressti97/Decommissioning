package hn.com.tigo.equipmentaccessoriesbilling.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentaccessoriesbilling.entities.ControlCancellationEntity;

@Repository
public interface IControlCancellationRepository extends JpaRepository<ControlCancellationEntity, Long> {

	Page<ControlCancellationEntity> findByTypeCancellation(Pageable pageable, Long typeCancellation);
}
