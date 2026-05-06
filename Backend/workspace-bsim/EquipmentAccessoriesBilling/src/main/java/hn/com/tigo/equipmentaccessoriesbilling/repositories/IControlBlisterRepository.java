package hn.com.tigo.equipmentaccessoriesbilling.repositories;

import hn.com.tigo.equipmentaccessoriesbilling.entities.ControlBlisterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IControlBlisterRepository extends JpaRepository<ControlBlisterEntity, Long> {

    boolean existsByModelIgnoreCase(String model);

    boolean existsByModelIgnoreCaseAndIdNot(String model, Long id);
}