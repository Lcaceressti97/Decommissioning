package hn.com.tigo.simcardinquiry.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hn.com.tigo.simcardinquiry.entities.SimcardVersionEntity;
import hn.com.tigo.simcardinquiry.models.SimcardVersionModel;
import hn.com.tigo.simcardinquiry.repositories.ISimcardVersionRepository;
import hn.com.tigo.simcardinquiry.services.interfaces.ILogsSimcardService;
import hn.com.tigo.simcardinquiry.services.interfaces.ISimcardVersionService;
import hn.com.tigo.simcardinquiry.utils.Constants;

@Service
public class SimcardVersionServiceImpl implements ISimcardVersionService {

	private final ISimcardVersionRepository simcardVersionRepository;
	private final ILogsSimcardService logsService;

	public SimcardVersionServiceImpl(ISimcardVersionRepository simcardVersionRepository,
			ILogsSimcardService logsService) {
		super();
		this.simcardVersionRepository = simcardVersionRepository;
		this.logsService = logsService;
	}

	@Override
	public List<SimcardVersionModel> getAll() {
		List<SimcardVersionEntity> entities = this.simcardVersionRepository
				.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));
		return entities.stream().map(SimcardVersionEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public List<SimcardVersionModel> getVersionByIdModel(Long idModel) {
		List<SimcardVersionEntity> entities = this.simcardVersionRepository.getVersionByIdModel(idModel);
		return entities.stream().map(SimcardVersionEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public SimcardVersionModel getById(Long id) {
		SimcardVersionEntity entity = this.simcardVersionRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));
		return entity.entityToModel();
	}

	@Override
	public void createVersion(SimcardVersionModel model) {
		try {
			SimcardVersionEntity entity = new SimcardVersionEntity();
			entity.setId(-1L);
			entity.setIdModel(model.getIdModel());
			entity.setVersion(model.getVersion());
			entity.setVersionSize(model.getVersionSize());
			entity.setVersionDescription(model.getVersionDescription());
			entity.setCapacity(model.getCapacity());
			entity.setVersionDetail(model.getVersionDetail());
			entity.setStatus(1L);
			entity.setCreatedDate(LocalDateTime.now());
			this.simcardVersionRepository.save(entity);
		} catch (BadRequestException e) {
			logsService.saveLog(6, 1L, Constants.BAD_REQUEST_EXCEPTION + e.getMessage());
			throw e;

		} catch (Exception e) {
			logsService.saveLog(6, 1L, Constants.EXCEPTION + e.getMessage());
			throw e;
		}

	}

	@Override
	public void updateVersion(Long id, SimcardVersionModel model) {
		try {
			SimcardVersionEntity entity = this.simcardVersionRepository.findById(id).orElse(null);
			if (entity == null)
				throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

			entity.setIdModel(model.getIdModel());
			entity.setVersion(model.getVersion());
			entity.setVersionSize(model.getVersionSize());
			entity.setVersionDescription(model.getVersionDescription());
			entity.setCapacity(model.getCapacity());
			entity.setVersionDetail(model.getVersionDetail());

			this.simcardVersionRepository.save(entity);
		} catch (BadRequestException e) {
			logsService.saveLog(6, 1L, Constants.BAD_REQUEST_EXCEPTION + e.getMessage());
			throw e;

		} catch (Exception e) {
			logsService.saveLog(6, 1L, Constants.EXCEPTION + e.getMessage());
			throw e;
		}

	}

	@Override
	public void deleteVersion(Long id) {
		SimcardVersionEntity entity = this.simcardVersionRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

		this.simcardVersionRepository.delete(entity);

	}

}
