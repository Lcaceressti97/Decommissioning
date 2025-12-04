package hn.com.tigo.equipmentaccessoriesbilling.services.interfaces;

import java.util.List;

import hn.com.tigo.equipmentaccessoriesbilling.entities.UserBranchOfficesEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.UserBranchOfficesModel;

public interface IUserBranchOfficesService {

	void add(UserBranchOfficesModel model);
	
	void registerOrCancelUser(Long idUser, Long idBranchOffices, Long status);
	
	List<UserBranchOfficesEntity> findByIdUser(Long idUser);
}
