package hn.com.tigo.equipmentaccessoriesbilling.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentaccessoriesbilling.entities.UserBranchOfficesEntity;

@Repository
public interface IUserBranchOfficesRepository extends JpaRepository<UserBranchOfficesEntity, Long> {

	@Modifying
	@Query("UPDATE UserBranchOfficesEntity ubo SET ubo.status = :status WHERE ubo.idUser = :idUser AND ubo.idBranchOffices = :idBranchOffices")
	void updateStatusByIdUserAndIdBranchOffices(@Param("idUser") Long idUser,
			@Param("idBranchOffices") Long idBranchOffices, @Param("status") Long status);
	
	@Query(value = "SELECT * FROM MEA_USER_BRANCH_OFFICES WHERE ID_USER = :idUser AND STATUS = 1", nativeQuery = true)
	List<UserBranchOfficesEntity> findByIdUserActivated(Long idUser);
	
	List<UserBranchOfficesEntity> findByIdUser(Long idUser);

}
