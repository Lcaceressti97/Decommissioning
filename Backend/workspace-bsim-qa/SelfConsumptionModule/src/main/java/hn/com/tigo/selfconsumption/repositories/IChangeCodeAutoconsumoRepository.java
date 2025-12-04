package hn.com.tigo.selfconsumption.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hn.com.tigo.selfconsumption.entities.ChangeCodeAutoconsumoEntity;

@Repository
public interface IChangeCodeAutoconsumoRepository extends JpaRepository<ChangeCodeAutoconsumoEntity, Long> {

}
