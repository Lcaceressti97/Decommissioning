package hn.com.tigo.simcardinquiry.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hn.com.tigo.simcardinquiry.entities.SimcardCustomerEntity;
import hn.com.tigo.simcardinquiry.models.SimcardCustomerModel;
import hn.com.tigo.simcardinquiry.repositories.ISimcardCustomerRepository;
import hn.com.tigo.simcardinquiry.services.interfaces.ILogsSimcardService;
import hn.com.tigo.simcardinquiry.services.interfaces.ISimcardCustomerService;
import hn.com.tigo.simcardinquiry.utils.Constants;

@Service
public class SimcardCustomerServiceImpl implements ISimcardCustomerService {

	private final ISimcardCustomerRepository simcardCustomerRepository;
	private final ILogsSimcardService logsService;

	public SimcardCustomerServiceImpl(ISimcardCustomerRepository simcardCustomerRepository, ILogsSimcardService logsService) {
		super();
		this.simcardCustomerRepository = simcardCustomerRepository;
		this.logsService = logsService;
	}

	@Override
	public List<SimcardCustomerModel> getAll() {
		List<SimcardCustomerEntity> entities = this.simcardCustomerRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));
		return entities.stream().map(SimcardCustomerEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public SimcardCustomerModel getById(Long id) {
		SimcardCustomerEntity entity = this.simcardCustomerRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));
		return entity.entityToModel();
	}

	@Override
	public void createCustomer(SimcardCustomerModel model) {
		try {
			SimcardCustomerEntity entity = new SimcardCustomerEntity();
			entity.setId(-1L);
			entity.setCode(model.getCode());
			entity.setHlr(model.getHlr());
			entity.setClientName(model.getClientName());
			entity.setDescriptionHrl(model.getDescriptionHrl());
			entity.setStatus(1L);
			entity.setCreatedDate(LocalDateTime.now());
			this.simcardCustomerRepository.save(entity);
		} catch (BadRequestException e) {
			logsService.saveLog(6, 1L, Constants.BAD_REQUEST_EXCEPTION + e.getMessage());
			throw e;

		} catch (Exception e) {
			logsService.saveLog(6, 1L, Constants.EXCEPTION + e.getMessage());
			throw e;
		}
	}

	@Override
	public void updateCustomer(Long id, SimcardCustomerModel model) {
		try {
			SimcardCustomerEntity entity = this.simcardCustomerRepository.findById(id).orElse(null);
			if (entity == null)
				throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

			entity.setCode(model.getCode());
			entity.setHlr(model.getHlr());
			entity.setClientName(model.getClientName());
			entity.setDescriptionHrl(model.getDescriptionHrl());
			this.simcardCustomerRepository.save(entity);

		} catch (BadRequestException e) {
			logsService.saveLog(6, 1L, Constants.BAD_REQUEST_EXCEPTION + e.getMessage());
			throw e;

		} catch (Exception e) {
			logsService.saveLog(6, 1L, Constants.EXCEPTION + e.getMessage());
			throw e;
		}
	}

	@Override
	public void deleteCustomer(Long id) {
		SimcardCustomerEntity entity = this.simcardCustomerRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

		this.simcardCustomerRepository.delete(entity);

	}

}
