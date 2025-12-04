package hn.com.tigo.equipmentaccessoriesbilling.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentaccessoriesbilling.entities.UsersEntity;
import javax.ws.rs.BadRequestException;

import hn.com.tigo.equipmentaccessoriesbilling.models.UsersModel;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IUsersRepository;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.ILogsService;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IUsersService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.Constants;

@Service
public class UsersServiceImpl implements IUsersService {

	private final IUsersRepository usersRepository;
	private final ILogsService logsService;

	public UsersServiceImpl(IUsersRepository usersRepository, ILogsService logsService) {
		super();
		this.usersRepository = usersRepository;
		this.logsService = logsService;
	}

	@Override
	public List<UsersModel> getUsersWithoutPermissions() {
		List<UsersEntity> entities = this.usersRepository.getUsersWithoutPermissions();
		return entities.stream().map(UsersEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public List<UsersModel> getByUserName(String userName) {
		List<UsersEntity> entities = usersRepository.findByUserName(userName.toUpperCase());

		if (entities.isEmpty()) {
			throw new BadRequestException(String.format(Constants.NOT_USER_RECORDS_FOUND, userName));
		}

		return entities.stream().map(UsersEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public List<UsersModel> getUsersByBranchOfficeAndStatus(Long idBranchOffices, Long status) {
		List<UsersEntity> entities = usersRepository.getUsersByBranchOfficeAndStatus(idBranchOffices, status);

		if (entities.isEmpty()) {
			throw new BadRequestException(Constants.ERROR_BRANCHE_OFFICE_NOT_RECORDS + idBranchOffices);
		}

		return entities.stream().map(UsersEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public UsersModel getById(Long id) {
		UsersEntity entity = this.usersRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID, id));
		return entity.entityToModel();
	}

	@Override
	public void add(UsersModel model) {
		long startTime = System.currentTimeMillis();
		try {
			UsersEntity entity = new UsersEntity();
			entity.setId(-1L);
			entity.setName(model.getName());
			entity.setUserName(model.getUserName());
			entity.setEmail(model.getEmail());
			entity.setTypeUser(model.getTypeUser());
			entity.setStatus(0L);
			entity.setCreated(LocalDateTime.now());
			this.usersRepository.save(entity);
		} catch (BadRequestException e) {
			long duration = System.currentTimeMillis() - startTime;

			logsService.saveLog(13, 1L, "An error occurred while creating the user.: " + e.getMessage(),
					model.getUserName(), duration);
			throw e;
		} catch (Exception e) {
			long duration = System.currentTimeMillis() - startTime;

			logsService.saveLog(13, 1L, "An error occurred while creating the user.: " + e.getMessage(),
					model.getUserName(), duration);
			throw e;
		}
	}

	@Override
	public void update(Long id, UsersModel model) {
		long startTime = System.currentTimeMillis();

		try {
			UsersEntity entity = this.usersRepository.findById(id).orElse(null);
			if (entity == null)
				throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_UPDATE, id));

			entity.setName(model.getName());
			entity.setUserName(model.getUserName());
			entity.setEmail(model.getEmail());
			entity.setTypeUser(model.getTypeUser());
			entity.setStatus(1L);
			entity.setCreated(LocalDateTime.now());
			this.usersRepository.save(entity);
		} catch (BadRequestException e) {
			long duration = System.currentTimeMillis() - startTime;

			logsService.saveLog(13, id, "An error occurred while updating the user.: " + e.getMessage(),
					model.getUserName(), duration);
			throw e;
		} catch (Exception e) {
			long duration = System.currentTimeMillis() - startTime;

			logsService.saveLog(13, id, "An error occurred while updating the user.: " + e.getMessage(),
					model.getUserName(), duration);
			throw e;
		}
	}

	@Override
	public void updateStatus(Long id, Long status) {
		long startTime = System.currentTimeMillis();

		try {
			UsersEntity entity = this.usersRepository.findById(id).orElse(null);
			if (entity == null)
				throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_UPDATE, id));

			entity.setStatus(status);
			entity.setCreated(LocalDateTime.now());
			this.usersRepository.save(entity);
		} catch (BadRequestException e) {
			long duration = System.currentTimeMillis() - startTime;

			logsService.saveLog(14, id, "An error occurred while updating the user status: " + e.getMessage(), null,
					duration);
			throw e;
		} catch (Exception e) {
			long duration = System.currentTimeMillis() - startTime;

			logsService.saveLog(14, id, "An error occurred while updating the user status: " + e.getMessage(), null,
					duration);
			throw e;
		}

	}

	@Override
	public void assignBranchUser(Long id, Long idBranchOffices) {
		long startTime = System.currentTimeMillis();

		try {
			UsersEntity entity = this.usersRepository.findById(id).orElse(null);
			if (entity == null)
				throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_UPDATE, id));

			this.usersRepository.save(entity);
		} catch (BadRequestException e) {
			long duration = System.currentTimeMillis() - startTime;

			logsService.saveLog(12, id, "An error occurred while assigning the user to the branch.: " + e.getMessage(),
					null, duration);
			throw e;
		} catch (Exception e) {
			long duration = System.currentTimeMillis() - startTime;

			logsService.saveLog(12, id, "An error occurred while assigning the user to the branch.: " + e.getMessage(),
					null, duration);
			throw e;
		}
	}

	@Override
	public void delete(Long id) {
		UsersEntity entity = this.usersRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_DELETE, id));

		this.usersRepository.delete(entity);
	}

	@Override
	public List<UsersEntity> findByUserName(String userName) {
		return usersRepository.findByUserName(userName.toUpperCase());
	}

}
