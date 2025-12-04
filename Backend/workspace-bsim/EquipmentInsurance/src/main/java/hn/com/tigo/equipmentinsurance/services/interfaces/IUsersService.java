package hn.com.tigo.equipmentinsurance.services.interfaces;

import java.util.List;

import hn.com.tigo.equipmentinsurance.entities.UsersEntity;

public interface IUsersService {

	List<UsersEntity> findByUserName(String userName);

}
