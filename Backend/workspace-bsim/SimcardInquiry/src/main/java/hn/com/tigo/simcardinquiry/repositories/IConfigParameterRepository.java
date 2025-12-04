package hn.com.tigo.simcardinquiry.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hn.com.tigo.simcardinquiry.entities.ConfigParameterEntity;

public interface IConfigParameterRepository extends JpaRepository<ConfigParameterEntity, Long> {

	List<ConfigParameterEntity> findByIdApplication(Long idApplication);
	
	ConfigParameterEntity findByParameterValue(String parameterValue);
	
	ConfigParameterEntity findByParameterName(String parameterName);

}
