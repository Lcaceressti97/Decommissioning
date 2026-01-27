package hn.com.tigo.equipmentaccessoriesbilling.repositories;

import hn.com.tigo.equipmentaccessoriesbilling.entities.ControlUnloadStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IControlUnloadStockRepository extends JpaRepository<ControlUnloadStockEntity, Long> {

    boolean existsByReferenceAndStatus(Long reference, String status);

}
