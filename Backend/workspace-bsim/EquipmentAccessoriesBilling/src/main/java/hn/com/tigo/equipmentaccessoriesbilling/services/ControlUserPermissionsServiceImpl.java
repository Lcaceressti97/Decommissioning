package hn.com.tigo.equipmentaccessoriesbilling.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentaccessoriesbilling.entities.ControlUserPermissionsEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.UsersEntity;

import javax.ws.rs.BadRequestException;
import hn.com.tigo.equipmentaccessoriesbilling.models.ControlUserPermissionsModel;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IControlUserPermissionsRepository;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IUsersRepository;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IControlUserPermissionsService;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.ILogsService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.Constants;

@Service
public class ControlUserPermissionsServiceImpl implements IControlUserPermissionsService {

	private final IControlUserPermissionsRepository controlUserPermissionsRepository;
	private final IUsersRepository usersRepository;
	private final ILogsService logsService;

	public ControlUserPermissionsServiceImpl(IControlUserPermissionsRepository controlUserPermissionsRepository,
			IUsersRepository usersRepository, ILogsService logsService) {
		super();
		this.controlUserPermissionsRepository = controlUserPermissionsRepository;
		this.usersRepository = usersRepository;
		this.logsService = logsService;
	}

	@Override
	public Page<ControlUserPermissionsModel> getAll(Pageable pageable) {
		Pageable descendingPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
				Sort.by("id").descending());

		Page<ControlUserPermissionsEntity> entities = this.controlUserPermissionsRepository.findAll(descendingPageable);
		return entities.map(ControlUserPermissionsEntity::entityToModel);
	}

	@Override
	public ControlUserPermissionsModel getByUserName(String userName) {
		ControlUserPermissionsEntity entities = controlUserPermissionsRepository.findByUserName(userName.toUpperCase());

		if (entities == null) {
			throw new BadRequestException(String.format(Constants.NOT_USER_RECORDS_FOUND, userName));
		}

		return entities.entityToModel();
	}

	@Override
	public List<ControlUserPermissionsModel> getIssuingUsers() {
		List<ControlUserPermissionsEntity> entities = controlUserPermissionsRepository.getIssuingUsers();

		return entities.stream().map(ControlUserPermissionsEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public ControlUserPermissionsModel getById(Long id) {
		ControlUserPermissionsEntity entity = this.controlUserPermissionsRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID, id));
		return entity.entityToModel();
	}

	@Override
	public void add(ControlUserPermissionsModel model) {
		long startTime = System.currentTimeMillis();

		try {
			UsersEntity userEntity = this.usersRepository.findById(model.getIdUser()).orElse(null);
			if (userEntity == null)
				throw new BadRequestException(String.format(Constants.NOT_USER_RECORDS_FOUND, model.getIdUser()));

			ControlUserPermissionsEntity entity = new ControlUserPermissionsEntity();
			entity.setId(-1L);
			entity.setIdUser(model.getIdUser());
			entity.setName(model.getName());
			entity.setUserName(model.getUserName().toUpperCase());
			entity.setEmail(model.getEmail());
			entity.setGenerateBill(model.getGenerateBill());
			entity.setIssueSellerInvoice(model.getIssueSellerInvoice());
			entity.setAuthorizeInvoice(model.getAuthorizeInvoice());
			entity.setTypeUser(model.getTypeUser());
			entity.setAssignDiscount(model.getAssignDiscount());
			entity.setCreated(LocalDateTime.now());
			this.controlUserPermissionsRepository.save(entity);
		} catch (BadRequestException e) {
			long duration = System.currentTimeMillis() - startTime;

			logsService.saveLog(9, model.getIdUser(),
					"An error occurred while creating the permission control: " + e.getMessage(), model.getUserName(),
					duration);
			throw e;

		} catch (Exception e) {
			long duration = System.currentTimeMillis() - startTime;

			logsService.saveLog(9, model.getIdUser(),
					"An error occurred while creating the permission control.: " + e.getMessage(), model.getUserName(),
					duration);
			throw e;
		}
	}

	@Override
	public void update(Long id, ControlUserPermissionsModel model) {
		long startTime = System.currentTimeMillis();

		try {
			ControlUserPermissionsEntity entity = this.controlUserPermissionsRepository.findById(id).orElse(null);
			if (entity == null)
				throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_UPDATE, id));

			UsersEntity userEntity = this.usersRepository.findById(model.getIdUser()).orElse(null);
			if (userEntity == null)
				throw new BadRequestException(String.format(Constants.NOT_USER_RECORDS_FOUND, model.getIdUser()));

			entity.setIdUser(model.getIdUser());
			entity.setName(model.getName());
			entity.setUserName(model.getUserName());
			entity.setEmail(model.getEmail());
			entity.setGenerateBill(model.getGenerateBill());
			entity.setIssueSellerInvoice(model.getIssueSellerInvoice());
			entity.setAuthorizeInvoice(model.getAuthorizeInvoice());
			entity.setTypeUser(model.getTypeUser());
			entity.setAssignDiscount(model.getAssignDiscount());
			entity.setCreated(LocalDateTime.now());
			this.controlUserPermissionsRepository.save(entity);
		} catch (BadRequestException e) {
			long duration = System.currentTimeMillis() - startTime;

			logsService.saveLog(9, model.getIdUser(),
					"An error occurred while updating the permission control: " + e.getMessage(), model.getUserName(),
					duration);
			throw e;

		} catch (Exception e) {
			long duration = System.currentTimeMillis() - startTime;

			logsService.saveLog(9, model.getIdUser(),
					"An error occurred while updating the permission control: " + e.getMessage(), model.getUserName(),
					duration);
			throw e;
		}
	}

	@Override
	public void delete(Long id) {
		ControlUserPermissionsEntity entity = this.controlUserPermissionsRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_DELETE, id));

		this.controlUserPermissionsRepository.delete(entity);
	}

}
