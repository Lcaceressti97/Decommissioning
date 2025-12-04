package hn.com.tigo.equipmentinsurance.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentinsurance.entities.ConfigParametersEntity;

@Repository
public interface IConfigParametersRepository extends JpaRepository<ConfigParametersEntity, Long> {

	List<ConfigParametersEntity> findByIdApplication(Long idApplication);
	
	ConfigParametersEntity findByParameterName(String parameterName);
	
}
