package hn.com.tigo.equipmentaccessoriesbilling.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentaccessoriesbilling.entities.ConfigParametersEntity;

@Repository
public interface IConfigParametersRepository extends JpaRepository<ConfigParametersEntity, Long> {

	List<ConfigParametersEntity> findByIdApplication(Long idApplication);

	ConfigParametersEntity findByParameterName(String parameterName);

	@Query(value = "SELECT PARAMETER_VALUE FROM MEA_CONFIG_PARAMETERS WHERE PARAMETER_NAME = :parameterName", nativeQuery = true)
	String findByParameterNameNative(@Param("parameterName") String parameterName);
	
}
