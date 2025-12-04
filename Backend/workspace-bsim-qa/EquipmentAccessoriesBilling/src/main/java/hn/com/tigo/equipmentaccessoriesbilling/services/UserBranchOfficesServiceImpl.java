package hn.com.tigo.equipmentaccessoriesbilling.services;

import java.time.LocalDateTime;
import java.util.List;

import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hn.com.tigo.equipmentaccessoriesbilling.entities.BranchOfficesEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.UserBranchOfficesEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.UsersEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.UserBranchOfficesModel;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IBranchOfficesRepository;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IUserBranchOfficesRepository;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IUsersRepository;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IUserBranchOfficesService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.Constants;

@Service
public class UserBranchOfficesServiceImpl implements IUserBranchOfficesService {

	private final IUserBranchOfficesRepository userBranchOfficesRepository;
	private final IUsersRepository usersRepository;
	private final IBranchOfficesRepository branchOfficesRepository;

	public UserBranchOfficesServiceImpl(IUserBranchOfficesRepository userBranchOfficesRepository,
			IUsersRepository usersRepository, IBranchOfficesRepository branchOfficesRepository) {
		super();
		this.userBranchOfficesRepository = userBranchOfficesRepository;
		this.usersRepository = usersRepository;
		this.branchOfficesRepository = branchOfficesRepository;

	}

	@Override
	public void add(UserBranchOfficesModel model) {

		UsersEntity userEntity = this.usersRepository.findById(model.getIdUser()).orElse(null);
		if (userEntity == null)
			throw new BadRequestException(String.format(Constants.NOT_USER_RECORDS_FOUND, model.getIdUser()));

		BranchOfficesEntity branchOfficesEntity = this.branchOfficesRepository.findById(model.getIdBranchOffices())
				.orElse(null);
		if (branchOfficesEntity == null)
			throw new BadRequestException(
					String.format(Constants.ERROR_BRANCHE_OFFICE_NOT_EXISTS, model.getIdBranchOffices()));

		//UserBranchOfficesEntity userBranchOfficesEntity = this.userBranchOfficesRepository
		//		.findByIdUserActivated(model.getIdUser());

		//if (userBranchOfficesEntity != null && model.getStatus() != 0) {
		//	throw new BadRequestException(String.format(Constants.USER_BRANCH_VALIDATION));
		//}

		UserBranchOfficesEntity entity = new UserBranchOfficesEntity();

		entity.setId(-1L);
		entity.setIdUser(model.getIdUser());
		entity.setIdBranchOffices(model.getIdBranchOffices());
		entity.setStatus(model.getStatus());
		entity.setCreated(LocalDateTime.now());
		this.userBranchOfficesRepository.save(entity);
	}

	@Override
	@Transactional
	public void registerOrCancelUser(Long idUser, Long idBranchOffices, Long status) {
		//UserBranchOfficesEntity userBranchOfficesEntity = this.userBranchOfficesRepository
		//		.findByIdUserActivated(idUser);

		//if (userBranchOfficesEntity != null && status != 0) {
		//	throw new BadRequestException(String.format(Constants.USER_BRANCH_VALIDATION));
		//}

		this.userBranchOfficesRepository.updateStatusByIdUserAndIdBranchOffices(idUser, idBranchOffices, status);
	}

	@Override
	public List<UserBranchOfficesEntity> findByIdUser(Long idUser) {

		List<UserBranchOfficesEntity> list = this.userBranchOfficesRepository.findByIdUser(idUser);

		return list;

	}

}
