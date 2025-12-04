package hn.com.tigo.simcardinquiry.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hn.com.tigo.simcardinquiry.entities.SimcardModelEntity;
import hn.com.tigo.simcardinquiry.models.SimcardModelModel;
import hn.com.tigo.simcardinquiry.repositories.ISimcardModelRepository;
import hn.com.tigo.simcardinquiry.services.interfaces.ILogsSimcardService;
import hn.com.tigo.simcardinquiry.services.interfaces.ISimcardModelService;
import hn.com.tigo.simcardinquiry.utils.Constants;

@Service
public class SimcardModelServiceImpl implements ISimcardModelService {

	private final ISimcardModelRepository simcardModelRepository;
	private final ILogsSimcardService logsService;

	public SimcardModelServiceImpl(ISimcardModelRepository simcardModelRepository, ILogsSimcardService logsService) {
		super();
		this.simcardModelRepository = simcardModelRepository;
		this.logsService = logsService;
	}

	@Override
	public List<SimcardModelModel> getAll() {
		List<SimcardModelEntity> entities = this.simcardModelRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));
		return entities.stream().map(SimcardModelEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public SimcardModelModel getById(Long id) {
		SimcardModelEntity entity = this.simcardModelRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));
		return entity.entityToModel();
	}

	@Override
	public void createModel(SimcardModelModel model) {
		try {
			SimcardModelEntity entity = new SimcardModelEntity();
			entity.setId(-1L);
			entity.setSimModel(model.getSimModel());
			entity.setModelDescription(model.getModelDescription());
			entity.setStatus(1L);
			entity.setCreatedDate(LocalDateTime.now());
			this.simcardModelRepository.save(entity);
		} catch (BadRequestException e) {
			logsService.saveLog(6, 1L, Constants.BAD_REQUEST_EXCEPTION + e.getMessage());
			throw e;

		} catch (Exception e) {
			logsService.saveLog(6, 1L, Constants.EXCEPTION + e.getMessage());
			throw e;
		}

	}

	@Override
	public void updateModel(Long id, SimcardModelModel model) {
		try {
			SimcardModelEntity entity = this.simcardModelRepository.findById(id).orElse(null);
			if (entity == null)
				throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

			entity.setSimModel(model.getSimModel());
			entity.setModelDescription(model.getModelDescription());

			this.simcardModelRepository.save(entity);
		} catch (BadRequestException e) {
			logsService.saveLog(6, 1L, Constants.BAD_REQUEST_EXCEPTION + e.getMessage());
			throw e;

		} catch (Exception e) {
			logsService.saveLog(6, 1L, Constants.EXCEPTION + e.getMessage());
			throw e;
		}

	}

	@Override
	public void deleteModel(Long id) {
		SimcardModelEntity entity = this.simcardModelRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

		this.simcardModelRepository.delete(entity);

	}

}
