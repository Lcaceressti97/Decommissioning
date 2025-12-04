package hn.com.tigo.selfconsumption.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hn.com.tigo.selfconsumption.entities.ChangeCodeHistoricalEntity;

@Repository
public interface IChangeCodeHistoricalRepository extends JpaRepository<ChangeCodeHistoricalEntity, Long> {

	List<ChangeCodeHistoricalEntity> getChangeCodeHistoricalByChargeCodeId(Long chargeCodeId);

}
