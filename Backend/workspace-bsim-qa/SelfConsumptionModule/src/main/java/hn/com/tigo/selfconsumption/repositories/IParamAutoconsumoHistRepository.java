package hn.com.tigo.selfconsumption.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hn.com.tigo.selfconsumption.entities.ParamAutoconsumoHistEntity;

@Repository
public interface IParamAutoconsumoHistRepository extends JpaRepository<ParamAutoconsumoHistEntity, Long> {

	List<ParamAutoconsumoHistEntity> getParamAutoconsumoHistByIdParameter(Long idParameter);
}
