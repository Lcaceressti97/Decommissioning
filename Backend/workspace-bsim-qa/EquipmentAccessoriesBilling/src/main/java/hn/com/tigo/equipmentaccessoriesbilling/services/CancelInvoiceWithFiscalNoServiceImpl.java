package hn.com.tigo.equipmentaccessoriesbilling.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentaccessoriesbilling.entities.CancelInvoiceWithFiscalNoEntity;
import hn.com.tigo.equipmentaccessoriesbilling.entities.UsersEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.CancelInvoiceWithFiscalNoModel;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.ICancelInvoiceWithFiscalNoRepository;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IUsersRepository;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.ICancelInvoiceWithFiscalNoService;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.ILogsService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.Constants;

@Service
public class CancelInvoiceWithFiscalNoServiceImpl implements ICancelInvoiceWithFiscalNoService {

	private final ICancelInvoiceWithFiscalNoRepository cancelInvoiceWithFiscalNoRepository;
	private final IUsersRepository usersRepository;
	private final ILogsService logsService;

	public CancelInvoiceWithFiscalNoServiceImpl(
			ICancelInvoiceWithFiscalNoRepository cancelInvoiceWithFiscalNoRepository, IUsersRepository usersRepository,
			ILogsService logsService) {
		super();
		this.cancelInvoiceWithFiscalNoRepository = cancelInvoiceWithFiscalNoRepository;
		this.usersRepository = usersRepository;
		this.logsService = logsService;
	}

	@Override
	public Page<CancelInvoiceWithFiscalNoModel> getAll(Pageable pageable) {
		Page<CancelInvoiceWithFiscalNoEntity> entities = this.cancelInvoiceWithFiscalNoRepository.findAll(pageable);
		return entities.map(CancelInvoiceWithFiscalNoEntity::entityToModel);
	}

	@Override
	public CancelInvoiceWithFiscalNoModel getById(Long id) {
		CancelInvoiceWithFiscalNoEntity entity = this.cancelInvoiceWithFiscalNoRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID, id));
		return entity.entityToModel();
	}

	@Override
	public List<CancelInvoiceWithFiscalNoModel> getByUserName(String userName) {
		List<CancelInvoiceWithFiscalNoEntity> entities = cancelInvoiceWithFiscalNoRepository.findByUserName(userName.toUpperCase());

		if (entities.isEmpty()) {
			throw new BadRequestException(String.format(Constants.NOT_USER_RECORDS_FOUND, userName));
		}

		return entities.stream().map(CancelInvoiceWithFiscalNoEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public void add(CancelInvoiceWithFiscalNoModel model) {
		long startTime = System.currentTimeMillis();

		try {
			UsersEntity userEntity = this.usersRepository.findById(model.getIdUser()).orElse(null);
			if (userEntity == null)
				throw new BadRequestException(String.format(Constants.NOT_USER_ID_RECORDS_FOUND, model.getIdUser()));

			CancelInvoiceWithFiscalNoEntity entity = new CancelInvoiceWithFiscalNoEntity();
			entity.setId(-1L);
			entity.setIdUser(model.getIdUser());
			entity.setUserName(model.getUserName().toUpperCase());
			entity.setPermitStatus(model.getPermitStatus());
			entity.setCreated(LocalDateTime.now());
			this.cancelInvoiceWithFiscalNoRepository.save(entity);
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
	public void update(Long id, CancelInvoiceWithFiscalNoModel model) {
		long startTime = System.currentTimeMillis();

		try {
			CancelInvoiceWithFiscalNoEntity entity = this.cancelInvoiceWithFiscalNoRepository.findById(id).orElse(null);
			if (entity == null)
				throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_UPDATE, id));

			UsersEntity userEntity = this.usersRepository.findById(model.getIdUser()).orElse(null);
			if (userEntity == null)
				throw new BadRequestException(String.format(Constants.NOT_USER_ID_RECORDS_FOUND, model.getIdUser()));

			entity.setIdUser(model.getIdUser());
			entity.setUserName(model.getUserName());
			entity.setPermitStatus(model.getPermitStatus());
			entity.setCreated(LocalDateTime.now());
			this.cancelInvoiceWithFiscalNoRepository.save(entity);

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
		CancelInvoiceWithFiscalNoEntity entity = this.cancelInvoiceWithFiscalNoRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_DELETE, id));

		this.cancelInvoiceWithFiscalNoRepository.delete(entity);
	}

}
