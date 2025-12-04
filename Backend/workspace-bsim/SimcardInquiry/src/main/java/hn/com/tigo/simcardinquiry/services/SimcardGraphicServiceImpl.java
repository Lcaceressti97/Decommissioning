package hn.com.tigo.simcardinquiry.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hn.com.tigo.simcardinquiry.entities.SimcardGraphicEntity;
import hn.com.tigo.simcardinquiry.models.SimcardGraphicModel;
import hn.com.tigo.simcardinquiry.repositories.ISimcardGraphicRepository;
import hn.com.tigo.simcardinquiry.services.interfaces.ILogsSimcardService;
import hn.com.tigo.simcardinquiry.services.interfaces.ISimcardGraphicService;
import hn.com.tigo.simcardinquiry.utils.Constants;

@Service
public class SimcardGraphicServiceImpl implements ISimcardGraphicService {

	private final ISimcardGraphicRepository simcardGraphicRepository;
	private final ILogsSimcardService logsService;

	public SimcardGraphicServiceImpl(ISimcardGraphicRepository simcardGraphicRepository,
			ILogsSimcardService logsService) {
		super();
		this.simcardGraphicRepository = simcardGraphicRepository;
		this.logsService = logsService;
	}

	@Override
	public List<SimcardGraphicModel> getAll() {
		List<SimcardGraphicEntity> entities = this.simcardGraphicRepository
				.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));
		return entities.stream().map(SimcardGraphicEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public SimcardGraphicModel getById(Long id) {
		SimcardGraphicEntity entity = this.simcardGraphicRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));
		return entity.entityToModel();
	}

	@Override
	public void createGraphic(SimcardGraphicModel model) {
		try {
			SimcardGraphicEntity entity = new SimcardGraphicEntity();
			entity.setId(-1L);
			entity.setGraphicRef(model.getGraphicRef());
			entity.setGraphicDescription(model.getGraphicDescription());
			entity.setStatus(1L);
			entity.setCreatedDate(LocalDateTime.now());
			this.simcardGraphicRepository.save(entity);
		} catch (BadRequestException e) {
			logsService.saveLog(6, 1L, Constants.BAD_REQUEST_EXCEPTION + e.getMessage());
			throw e;

		} catch (Exception e) {
			logsService.saveLog(6, 1L, Constants.EXCEPTION + e.getMessage());
			throw e;
		}

	}

	@Override
	public void updateGraphic(Long id, SimcardGraphicModel model) {
		try {
			SimcardGraphicEntity entity = this.simcardGraphicRepository.findById(id).orElse(null);
			if (entity == null)
				throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

			entity.setGraphicRef(model.getGraphicRef());
			entity.setGraphicDescription(model.getGraphicDescription());

			this.simcardGraphicRepository.save(entity);
		} catch (BadRequestException e) {
			logsService.saveLog(6, 1L, Constants.BAD_REQUEST_EXCEPTION + e.getMessage());
			throw e;

		} catch (Exception e) {
			logsService.saveLog(6, 1L, Constants.EXCEPTION + e.getMessage());
			throw e;
		}

	}

	@Override
	public void deleteGraphic(Long id) {
		SimcardGraphicEntity entity = this.simcardGraphicRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

		this.simcardGraphicRepository.delete(entity);

	}

}
