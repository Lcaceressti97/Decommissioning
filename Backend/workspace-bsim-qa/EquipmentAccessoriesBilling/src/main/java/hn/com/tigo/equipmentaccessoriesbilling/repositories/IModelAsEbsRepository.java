package hn.com.tigo.equipmentaccessoriesbilling.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentaccessoriesbilling.entities.ModelAsEbsEntity;

import java.util.List;

@Repository
public interface IModelAsEbsRepository  extends JpaRepository<ModelAsEbsEntity, Long> {

	@Query(value = "SELECT * FROM MEA_MODELS_AS_EBS ORDER BY ID DESC", nativeQuery = true)
	Page<ModelAsEbsEntity> getAllModelAsEbs(Pageable pageable);

	List<ModelAsEbsEntity> getModelAsEbsByCodEbs(String codEbs);

	ModelAsEbsEntity findByCodMod(String codMod);

}
