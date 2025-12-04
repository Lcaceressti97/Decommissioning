package hn.com.tigo.equipmentaccessoriesbilling.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentaccessoriesbilling.entities.ControlAuthEmissionEntity;

@Repository
public interface IControlAuthEmissionRepository extends JpaRepository<ControlAuthEmissionEntity, Long> {

	List<ControlAuthEmissionEntity> findByUserCreate(String userCreate);

	Page<ControlAuthEmissionEntity> findByTypeApproval(Pageable pageable, Long typeApproval);

	ControlAuthEmissionEntity findTopByIdPrefectureOrderByCreatedDesc(Long idPrefecture);

}
