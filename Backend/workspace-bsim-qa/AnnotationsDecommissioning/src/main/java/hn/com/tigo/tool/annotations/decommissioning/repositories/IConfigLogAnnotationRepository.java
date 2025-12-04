package hn.com.tigo.tool.annotations.decommissioning.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hn.com.tigo.tool.annotations.decommissioning.entities.ConfigLogAnnotationEntity;

@Repository
public interface IConfigLogAnnotationRepository extends JpaRepository<ConfigLogAnnotationEntity, Long> {

	ConfigLogAnnotationEntity findByProjectAndMethodContaining(String project, String method);
}
