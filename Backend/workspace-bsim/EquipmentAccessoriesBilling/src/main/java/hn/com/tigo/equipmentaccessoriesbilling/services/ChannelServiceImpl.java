package hn.com.tigo.equipmentaccessoriesbilling.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentaccessoriesbilling.entities.ChannelEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.ChannelModel;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IChannelRepository;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IChannelService;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.ILogsService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.Constants;

@Service
public class ChannelServiceImpl implements IChannelService {

	private final IChannelRepository channelRepository;
	private final ILogsService logsService;

	public ChannelServiceImpl(IChannelRepository channelRepository, ILogsService logsService) {
		super();
		this.channelRepository = channelRepository;
		this.logsService = logsService;
	}

	@Override
	public List<ChannelModel> getAllChannels() {
		List<ChannelEntity> entities = this.channelRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
		return entities.stream().map(ChannelEntity::entityToModel).collect(Collectors.toList());

	}

	@Override
	public ChannelModel getChannelById(Long id) {
		ChannelEntity entity = this.channelRepository.findById(id).orElse(null);

		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

		return entity.entityToModel();
	}

	@Override
	public void addChannel(ChannelModel model) {
		long startTime = System.currentTimeMillis();
		try {
			ChannelEntity entity = new ChannelEntity();
			entity.setId(-1L);
			entity.setName(model.getName());
			entity.setDescription(model.getDescription());
			entity.setPayUpFrontNumber(model.getPayUpFrontNumber());
			entity.setNonFiscalNote(model.getNonFiscalNote());
			entity.setReserveSerialNumber(model.getReserveSerialNumber());
			entity.setReleaseSerialNumber(model.getReleaseSerialNumber());
			entity.setInventoryDownload(model.getInventoryDownload());
			entity.setGenerateTrama(model.getGenerateTrama());
			entity.setLogs(model.getLogs());
			entity.setStatus(1L);
			entity.setCreated(LocalDateTime.now());
			this.channelRepository.save(entity);
		} catch (BadRequestException e) {
			long duration = System.currentTimeMillis() - startTime;
			logsService.saveLog(19, model.getId(), "Error occurred while adding Channel: " + e.getMessage(), null,
					duration);
			throw e;

		} catch (Exception e) {
			long duration = System.currentTimeMillis() - startTime;
			logsService.saveLog(19, model.getId(), "Error occurred while adding Channel: " + e.getMessage(), null,
					duration);
			throw e;
		}

	}

	@Override
	public void updateChannel(Long id, ChannelModel model) {
		long startTime = System.currentTimeMillis();
		try {
			ChannelEntity entity = this.channelRepository.findById(id).orElse(null);
			if (entity == null)
				throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID, id));

			entity.setName(model.getName());
			entity.setDescription(model.getDescription());
			entity.setPayUpFrontNumber(model.getPayUpFrontNumber());
			entity.setNonFiscalNote(model.getNonFiscalNote());
			entity.setReserveSerialNumber(model.getReserveSerialNumber());
			entity.setReleaseSerialNumber(model.getReleaseSerialNumber());
			entity.setInventoryDownload(model.getInventoryDownload());
			entity.setGenerateTrama(model.getGenerateTrama());
			entity.setLogs(model.getLogs());
			entity.setStatus(model.getStatus());
			this.channelRepository.save(entity);
		} catch (BadRequestException e) {
			long duration = System.currentTimeMillis() - startTime;
			logsService.saveLog(19, model.getId(), "Error occurred while update Channel: " + e.getMessage(), null,
					duration);
			throw e;

		} catch (Exception e) {
			long duration = System.currentTimeMillis() - startTime;
			logsService.saveLog(19, model.getId(), "Error occurred while update Channel: " + e.getMessage(), null,
					duration);
			throw e;
		}

	}

	@Override
	public void deleteChannel(Long id) {
		ChannelEntity entity = this.channelRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_DELETE, id));

		this.channelRepository.delete(entity);

	}

}
