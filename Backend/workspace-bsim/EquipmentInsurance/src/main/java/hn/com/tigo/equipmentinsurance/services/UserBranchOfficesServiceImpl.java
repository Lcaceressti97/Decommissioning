package hn.com.tigo.equipmentinsurance.services;

import java.util.List;

import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentinsurance.entities.UserBranchOfficesEntity;
import hn.com.tigo.equipmentinsurance.repositories.IBranchOfficesRepository;
import hn.com.tigo.equipmentinsurance.repositories.IUserBranchOfficesRepository;
import hn.com.tigo.equipmentinsurance.repositories.IUsersRepository;
import hn.com.tigo.equipmentinsurance.services.interfaces.IUserBranchOfficesService;

@Service
public class UserBranchOfficesServiceImpl implements IUserBranchOfficesService {

	private final IUserBranchOfficesRepository userBranchOfficesRepository;

	public UserBranchOfficesServiceImpl(IUserBranchOfficesRepository userBranchOfficesRepository,
			IUsersRepository usersRepository, IBranchOfficesRepository branchOfficesRepository) {
		super();
		this.userBranchOfficesRepository = userBranchOfficesRepository;

	}

	@Override
	public List<UserBranchOfficesEntity> findByIdUser(Long idUser) {

		List<UserBranchOfficesEntity> list = this.userBranchOfficesRepository.findByIdUser(idUser);

		return list;

	}

}
