package hn.com.tigo.bsimmodule.repositories;

import java.util.List;

import hn.com.tigo.bsimmodule.entities.ConfigParametersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IConfigParametersRepository extends JpaRepository<ConfigParametersEntity, Long> {

    List<ConfigParametersEntity> findByIdApplication(Long idApplication);
}