package hn.com.tigo.tool.annotations.decommissioning.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hn.com.tigo.tool.annotations.decommissioning.entities.ParametersEntity;

@Repository
public interface IConfigParametersAnnotationRepository extends JpaRepository<ParametersEntity, Long> {

}
