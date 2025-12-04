package hn.com.tigo.equipmentinsurance.services;

import java.util.List;

import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentinsurance.entities.UsersEntity;
import hn.com.tigo.equipmentinsurance.repositories.IUsersRepository;
import hn.com.tigo.equipmentinsurance.services.interfaces.IUsersService;

@Service
public class UsersServiceImpl implements IUsersService {

	private final IUsersRepository usersRepository;

	public UsersServiceImpl(IUsersRepository usersRepository) {
		super();
		this.usersRepository = usersRepository;
	}

	@Override
	public List<UsersEntity> findByUserName(String userName) {
		List<UsersEntity> entities = usersRepository.findByUserName(userName);

		return entities;
	}

}
