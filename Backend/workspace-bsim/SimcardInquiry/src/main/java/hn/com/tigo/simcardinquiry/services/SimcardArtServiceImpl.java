package hn.com.tigo.simcardinquiry.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hn.com.tigo.simcardinquiry.entities.SimcardArtEntity;
import hn.com.tigo.simcardinquiry.models.SimcardArtModel;
import hn.com.tigo.simcardinquiry.repositories.ISimcardArtRepository;
import hn.com.tigo.simcardinquiry.services.interfaces.ILogsSimcardService;
import hn.com.tigo.simcardinquiry.services.interfaces.ISimcardArtService;
import hn.com.tigo.simcardinquiry.utils.Constants;

@Service
public class SimcardArtServiceImpl implements ISimcardArtService {

	private final ISimcardArtRepository simcardArtRepository;
	private final ILogsSimcardService logsService;

	public SimcardArtServiceImpl(ISimcardArtRepository simcardArtRepository, ILogsSimcardService logsService) {
		super();
		this.simcardArtRepository = simcardArtRepository;
		this.logsService = logsService;
	}

	@Override
	public List<SimcardArtModel> getAll() {
		List<SimcardArtEntity> entities = this.simcardArtRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));
		return entities.stream().map(SimcardArtEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public SimcardArtModel getById(Long id) {
		SimcardArtEntity entity = this.simcardArtRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));
		return entity.entityToModel();
	}

	@Override
	public void createArt(SimcardArtModel model) {
		try {
			SimcardArtEntity entity = new SimcardArtEntity();
			entity.setId(-1L);
			entity.setArtNumber(model.getArtNumber());
			entity.setArtDescription(model.getArtDescription());
			entity.setStatus(1L);
			entity.setCreatedDate(LocalDateTime.now());
			this.simcardArtRepository.save(entity);
		} catch (BadRequestException e) {
			logsService.saveLog(6, 1L, Constants.BAD_REQUEST_EXCEPTION + e.getMessage());
			throw e;

		} catch (Exception e) {
			logsService.saveLog(6, 1L, Constants.EXCEPTION + e.getMessage());
			throw e;
		}

	}

	@Override
	public void updateArt(Long id, SimcardArtModel model) {
		try {
			SimcardArtEntity entity = this.simcardArtRepository.findById(id).orElse(null);
			if (entity == null)
				throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

			entity.setArtNumber(model.getArtNumber());
			entity.setArtDescription(model.getArtDescription());
			this.simcardArtRepository.save(entity);
		} catch (BadRequestException e) {
			logsService.saveLog(6, 1L, Constants.BAD_REQUEST_EXCEPTION + e.getMessage());
			throw e;

		} catch (Exception e) {
			logsService.saveLog(6, 1L, Constants.EXCEPTION + e.getMessage());
			throw e;
		}

	}

	@Override
	public void deleteArt(Long id) {
		SimcardArtEntity entity = this.simcardArtRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

		this.simcardArtRepository.delete(entity);

	}

}
