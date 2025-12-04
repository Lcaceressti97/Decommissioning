package hn.com.tigo.equipmentinsurance.services.interfaces;

import java.util.List;

import hn.com.tigo.equipmentinsurance.entities.UserBranchOfficesEntity;

public interface IUserBranchOfficesService {

	List<UserBranchOfficesEntity> findByIdUser(Long idUser);
}
