package hn.com.tigo.simcardinquiry.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hn.com.tigo.simcardinquiry.entities.SimcardVersionEntity;

@Repository
public interface ISimcardVersionRepository extends JpaRepository<SimcardVersionEntity, Long> {

	List<SimcardVersionEntity> getVersionByIdModel(Long idModel);

	SimcardVersionEntity getCapacityByIdModel(Long idModel);

}
