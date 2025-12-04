package hn.com.tigo.equipmentaccessoriesbilling.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import hn.com.tigo.equipmentaccessoriesbilling.entities.BranchOfficesEntity;

@Repository
public interface IBranchOfficesRepository extends JpaRepository<BranchOfficesEntity, Long> {

	List<BranchOfficesEntity> getBranchOfficesByWineryCode(String wineryCode);

	boolean existsByIdPoint(Long idPoint);

    boolean existsByWineryCode(String wineryCode);

    boolean existsByWineryCodeAndIdNot(String wineryCode, Long id);

	@Query("SELECT b FROM BranchOfficesEntity b WHERE b.wineryCode = :wineryCode")
	BranchOfficesEntity getBranchOfficeByWineryCode(@Param("wineryCode") String wineryCode);
}
