package hn.com.tigo.equipmentaccessoriesbilling.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentaccessoriesbilling.entities.CancelInvoiceWithoutFiscalNoEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.UsersEntity;

import javax.ws.rs.BadRequestException;
import hn.com.tigo.equipmentaccessoriesbilling.models.CancelInvoiceWithoutFiscalNoModel;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.ICancelInvoiceWithoutFiscalNoRepository;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IUsersRepository;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.ICancelInvoiceWithoutFiscalNoService;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.ILogsService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.Constants;

@Service
public class CancelInvoiceWithoutFiscalNoServiceImpl implements ICancelInvoiceWithoutFiscalNoService {

	private final ICancelInvoiceWithoutFiscalNoRepository cancelInvoiceWithoutFiscalNoRepository;
	private final IUsersRepository usersRepository;
	private final ILogsService logsService;

	public CancelInvoiceWithoutFiscalNoServiceImpl(
			ICancelInvoiceWithoutFiscalNoRepository cancelInvoiceWithoutFiscalNoRepository,
			IUsersRepository usersRepository, ILogsService logsService) {
		super();
		this.cancelInvoiceWithoutFiscalNoRepository = cancelInvoiceWithoutFiscalNoRepository;
		this.usersRepository = usersRepository;
		this.logsService = logsService;
	}

	@Override
	public Page<CancelInvoiceWithoutFiscalNoModel> getAll(Pageable pageable) {
		Page<CancelInvoiceWithoutFiscalNoEntity> entities = this.cancelInvoiceWithoutFiscalNoRepository
				.findAll(pageable);
		return entities.map(CancelInvoiceWithoutFiscalNoEntity::entityToModel);
	}

	@Override
	public CancelInvoiceWithoutFiscalNoModel getById(Long id) {
		CancelInvoiceWithoutFiscalNoEntity entity = this.cancelInvoiceWithoutFiscalNoRepository.findById(id)
				.orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID, id));
		return entity.entityToModel();
	}

	@Override
	public List<CancelInvoiceWithoutFiscalNoModel> getByUserName(String userName) {
		List<CancelInvoiceWithoutFiscalNoEntity> entities = cancelInvoiceWithoutFiscalNoRepository
				.findByUserName(userName.toUpperCase());

		if (entities.isEmpty()) {
			throw new BadRequestException(String.format(Constants.NOT_USER_RECORDS_FOUND, userName));
		}

		return entities.stream().map(CancelInvoiceWithoutFiscalNoEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public void add(CancelInvoiceWithoutFiscalNoModel model) {
		long startTime = System.currentTimeMillis();

		try {
			UsersEntity userEntity = this.usersRepository.findById(model.getIdUser()).orElse(null);
			if (userEntity == null)
				throw new BadRequestException(String.format(Constants.NOT_USER_ID_RECORDS_FOUND, model.getIdUser()));

			CancelInvoiceWithoutFiscalNoEntity entity = new CancelInvoiceWithoutFiscalNoEntity();
			entity.setId(-1L);
			entity.setIdUser(model.getIdUser());
			entity.setUserName(model.getUserName().toUpperCase());
			entity.setPermitStatus(model.getPermitStatus());
			entity.setCreated(LocalDateTime.now());
			this.cancelInvoiceWithoutFiscalNoRepository.save(entity);
		} catch (BadRequestException e) {
			long duration = System.currentTimeMillis() - startTime;

			logsService.saveLog(10, model.getIdUser(),
					"Error occurred while adding the override permission assignment: " + e.getMessage(),
					model.getUserName(), duration);
			throw e;

		} catch (Exception e) {
			long duration = System.currentTimeMillis() - startTime;

			logsService.saveLog(10, model.getIdUser(),
					"Error occurred while adding the override permission assignment: " + e.getMessage(),
					model.getUserName(), duration);
			throw e;
		}
	}

	@Override
	public void update(Long id, CancelInvoiceWithoutFiscalNoModel model) {
		long startTime = System.currentTimeMillis();

		try {
			CancelInvoiceWithoutFiscalNoEntity entity = this.cancelInvoiceWithoutFiscalNoRepository.findById(id)
					.orElse(null);
			if (entity == null)
				throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_UPDATE, id));

			UsersEntity userEntity = this.usersRepository.findById(model.getIdUser()).orElse(null);
			if (userEntity == null)
				throw new BadRequestException(String.format(Constants.NOT_USER_ID_RECORDS_FOUND, model.getIdUser()));

			entity.setIdUser(model.getIdUser());
			entity.setUserName(model.getUserName());
			entity.setPermitStatus(model.getPermitStatus());
			entity.setCreated(LocalDateTime.now());
			this.cancelInvoiceWithoutFiscalNoRepository.save(entity);
		} catch (BadRequestException e) {
			long duration = System.currentTimeMillis() - startTime;

			logsService.saveLog(10, model.getIdUser(),
					"Error occurred while update the override permission assignment: " + e.getMessage(),
					model.getUserName(), duration);
			throw e;

		} catch (Exception e) {
			long duration = System.currentTimeMillis() - startTime;

			logsService.saveLog(10, model.getIdUser(),
					"Error occurred while update the override permission assignment: " + e.getMessage(),
					model.getUserName(), duration);
			throw e;
		}
	}

	@Override
	public void delete(Long id) {
		CancelInvoiceWithoutFiscalNoEntity entity = this.cancelInvoiceWithoutFiscalNoRepository.findById(id)
				.orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_DELETE, id));

		this.cancelInvoiceWithoutFiscalNoRepository.delete(entity);
	}

}
