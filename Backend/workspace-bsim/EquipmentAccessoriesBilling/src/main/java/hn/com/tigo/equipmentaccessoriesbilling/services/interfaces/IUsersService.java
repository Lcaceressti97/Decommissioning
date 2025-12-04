package hn.com.tigo.equipmentaccessoriesbilling.services.interfaces;

import java.util.List;

import hn.com.tigo.equipmentaccessoriesbilling.entities.UsersEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.UsersModel;

public interface IUsersService {

	List<UsersModel> getUsersWithoutPermissions();

	List<UsersModel> getByUserName(String userName);
	
	List<UsersModel> getUsersByBranchOfficeAndStatus(Long idBranchOffices, Long status);

	UsersModel getById(Long id);
	
	List<UsersEntity> findByUserName(String userName);

	void add(UsersModel model);

	void update(Long id, UsersModel model);

	void updateStatus(Long id, Long status);

	void assignBranchUser(Long id, Long idBranchOffices);

	void delete(Long id);

}
