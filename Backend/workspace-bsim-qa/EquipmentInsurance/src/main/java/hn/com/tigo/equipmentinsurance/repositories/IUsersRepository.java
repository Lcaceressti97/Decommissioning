package hn.com.tigo.equipmentinsurance.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hn.com.tigo.equipmentinsurance.entities.UsersEntity;

@Repository
public interface IUsersRepository extends JpaRepository<UsersEntity, Long> {

	@Query(value = "SELECT * FROM MEA_USERS WHERE  STATUS = 0 ORDER BY CREATED DESC", nativeQuery = true)
	List<UsersEntity> getUsersWithoutPermissions();

	@Query(value = "SELECT U.ID, U.NAME, U.USER_NAME, U.EMAIL, U.TYPE_USER, UBO.STATUS, UBO.CREATED FROM MEA_USER_BRANCH_OFFICES UBO JOIN MEA_USERS U ON UBO.ID_USER = U.ID WHERE UBO.ID_BRANCH_OFFICES = :idBranchOffices AND UBO.STATUS = :status  ORDER BY UBO.CREATED DESC", nativeQuery = true)
	List<UsersEntity> getUsersByBranchOfficeAndStatus(Long idBranchOffices, Long status);

	List<UsersEntity> findByUserName(String userName);

	@Query(value = "select c.winery_code from mea_users a inner join mea_user_branch_offices b on a.id = b.id_user inner join mea_branch_offices c on c.id = b.id_branch_offices where a.user_name = :userName and c.status = 1 and a.status = 1 and b.status = 1", nativeQuery = true)
	List<String> findWineryCodesByUserName(String userName);

}