package hn.com.tigo.simcardinquiry.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hn.com.tigo.simcardinquiry.entities.SimcardTypeEntity;
import hn.com.tigo.simcardinquiry.models.SimcardTypeModel;
import hn.com.tigo.simcardinquiry.repositories.ISimcardTypeRepository;
import hn.com.tigo.simcardinquiry.services.interfaces.ILogsSimcardService;
import hn.com.tigo.simcardinquiry.services.interfaces.ISimcardTypeService;
import hn.com.tigo.simcardinquiry.utils.Constants;

@Service
public class SimcardTypeServiceImpl implements ISimcardTypeService {

	private final ISimcardTypeRepository simcardTypeRepository;
	private final ILogsSimcardService logsService;

	public SimcardTypeServiceImpl(ISimcardTypeRepository simcardTypeRepository, ILogsSimcardService logsService) {
		super();
		this.simcardTypeRepository = simcardTypeRepository;
		this.logsService = logsService;
	}

	@Override
	public List<SimcardTypeModel> getAll() {
		List<SimcardTypeEntity> entities = this.simcardTypeRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));
		return entities.stream().map(SimcardTypeEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public SimcardTypeModel getById(Long id) {
		SimcardTypeEntity entity = this.simcardTypeRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));
		return entity.entityToModel();
	}

	@Override
	public void createType(SimcardTypeModel model) {
		try {
			SimcardTypeEntity entity = new SimcardTypeEntity();
			entity.setId(-1L);
			entity.setTypeNumber(model.getTypeNumber());
			entity.setTypeDescription(model.getTypeDescription());
			entity.setStatus(1L);
			entity.setCreatedDate(LocalDateTime.now());
			this.simcardTypeRepository.save(entity);
		} catch (BadRequestException e) {
			logsService.saveLog(6, 1L, Constants.BAD_REQUEST_EXCEPTION + e.getMessage());
			throw e;

		} catch (Exception e) {
			logsService.saveLog(6, 1L, Constants.EXCEPTION + e.getMessage());
			throw e;
		}
	}

	@Override
	public void updateType(Long id, SimcardTypeModel model) {
		try {
			SimcardTypeEntity entity = this.simcardTypeRepository.findById(id).orElse(null);
			if (entity == null)
				throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

			entity.setTypeNumber(model.getTypeNumber());
			entity.setTypeDescription(model.getTypeDescription());

			this.simcardTypeRepository.save(entity);
		} catch (BadRequestException e) {
			logsService.saveLog(6, 1L, Constants.BAD_REQUEST_EXCEPTION + e.getMessage());
			throw e;

		} catch (Exception e) {
			logsService.saveLog(6, 1L, Constants.EXCEPTION + e.getMessage());
			throw e;
		}
	}

	@Override
	public void deleteType(Long id) {
		SimcardTypeEntity entity = this.simcardTypeRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

		this.simcardTypeRepository.delete(entity);
	}

}
