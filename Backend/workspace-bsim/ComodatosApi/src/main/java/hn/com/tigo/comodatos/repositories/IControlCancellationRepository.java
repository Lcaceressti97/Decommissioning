package hn.com.tigo.comodatos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hn.com.tigo.comodatos.entities.ControlCancellationEntity;

@Repository
public interface IControlCancellationRepository extends JpaRepository<ControlCancellationEntity, Long>{

}
