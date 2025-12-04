package hn.com.tigo.selfconsumption.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hn.com.tigo.selfconsumption.entities.ParametersAutoconsumoEntity;

@Repository
public interface IParametersAutoconsumoRepository extends JpaRepository<ParametersAutoconsumoEntity, Long> {

	List<ParametersAutoconsumoEntity> findByIdApplication(Long idApplication);

}
