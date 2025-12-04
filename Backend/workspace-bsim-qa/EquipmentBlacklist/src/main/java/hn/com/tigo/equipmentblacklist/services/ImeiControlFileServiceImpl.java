package hn.com.tigo.equipmentblacklist.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import hn.com.tigo.equipmentblacklist.entities.ImeiControlFileEntity;
import hn.com.tigo.equipmentblacklist.models.ImeiControlFileModel;
import hn.com.tigo.equipmentblacklist.repositories.ImeiControlFileRepository;
import hn.com.tigo.equipmentblacklist.services.interfaces.ILogsService;
import hn.com.tigo.equipmentblacklist.services.interfaces.ImeiControlFileService;
import hn.com.tigo.equipmentblacklist.utils.Constants;

@Service
public class ImeiControlFileServiceImpl implements ImeiControlFileService {

	private final ImeiControlFileRepository imeiControlFileRepository;
	private final ILogsService logsService;

	public ImeiControlFileServiceImpl(ImeiControlFileRepository imeiControlFileRepository, ILogsService logsService) {
		this.imeiControlFileRepository = imeiControlFileRepository;
		this.logsService = logsService;
	}

	@Override
	public List<ImeiControlFileModel> getAll() {
		List<ImeiControlFileEntity> entities = this.imeiControlFileRepository.getAllByStatusActive();
		return entities.stream().map(ImeiControlFileEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public ImeiControlFileModel getById(Long id) {
		ImeiControlFileEntity entity = this.imeiControlFileRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.RECORDNOTFOUNDID, id));
		return entity.entityToModel();
	}

	@Override
	public List<ImeiControlFileModel> getByPhone(String phone) {
		List<ImeiControlFileEntity> entities;
		String phoneValue = phone.trim();
		entities = this.imeiControlFileRepository.getByPhoneAndStatus(phoneValue);
		if (entities.isEmpty())
			throw new BadRequestException(String.format(Constants.RECORDNOTFOUNDPHONE, phoneValue));
		return entities.stream().map(ImeiControlFileEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public List<ImeiControlFileModel> findByPhoneOrImeiWithType(int type, String value) {
		List<ImeiControlFileEntity> entities;
		entities = this.imeiControlFileRepository.findByPhoneOrImeiWithType(type, value);
		if (entities.isEmpty() && type == 1) {
			throw new BadRequestException(String.format(Constants.RECORDNOTFOUNDPHONE, value));
		} else if (entities.isEmpty() && type == 2) {
			throw new BadRequestException(String.format(Constants.RECORDNOTFOUNDIMEI, value));
		}
		return entities.stream().map(ImeiControlFileEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public ImeiControlFileModel findByPhoneAndStatus(String phone, Long status) {
		ImeiControlFileEntity entity = this.imeiControlFileRepository.findByPhoneAndStatus(phone, status);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.RECORDNOTFOUNDPHONE, phone));
		return entity.entityToModel();
	}

	@Override
	public void add(ImeiControlFileModel model) {
		try {

			ImeiControlFileEntity recordExists = imeiControlFileRepository.findByPhoneAndStatus(model.getPhone().trim(),
					1L);

			if (recordExists != null) {
				recordExists.setStatus(0L);
				imeiControlFileRepository.save(recordExists);
			}

			ImeiControlFileEntity entity = new ImeiControlFileEntity();
			entity.setId(-1L);
			entity.setTransactionId(model.getTransactionId());
			entity.setPhone(model.getPhone().trim());
			entity.setImei(model.getImei().trim());
			entity.setImsi(model.getImsi());
			entity.setStatus(1L);
			entity.setCreatedDate(LocalDateTime.now());
			this.imeiControlFileRepository.save(entity);

		} catch (DataIntegrityViolationException e) {
			logsService.saveLog(1, model.getId(), Constants.DATAINTREGITYVIOLATIONEXCEPTION + e.getMessage());
			throw new BadRequestException(Constants.DATAINTREGITYVIOLATIONEXCEPTION, e);

		} catch (BadRequestException e) {
			logsService.saveLog(1, model.getStatus(), Constants.BADREQUESTEXCEPTION + e.getMessage());
			throw e;
		} catch (Exception e) {
			logsService.saveLog(1, model.getStatus(), Constants.EXCEPTION + e.getMessage());
			throw e;
		}
	}

	@Override
	public void update(Long id, ImeiControlFileModel model) {
		try {
			ImeiControlFileEntity entity = this.imeiControlFileRepository.findById(id).orElse(null);
			if (entity == null)
				throw new BadRequestException(String.format(Constants.RECORDNOTFOUNDID, id));

			entity.setTransactionId(model.getTransactionId());
			entity.setPhone(model.getPhone().trim());
			entity.setImei(model.getImei().trim());
			entity.setImsi(model.getImsi());
			entity.setStatus(model.getStatus());
			entity.setCreatedDate(LocalDateTime.now());
			this.imeiControlFileRepository.saveAndFlush(entity);
		} catch (BadRequestException e) {
			logsService.saveLog(1, id, Constants.BADREQUESTEXCEPTIONUPDATE + e.getMessage());
			throw e;
		} catch (Exception e) {
			logsService.saveLog(1, id, Constants.EXCEPTIONUPDATE + e.getMessage());
			throw e;
		}
	}

	@Override
	public void delete(Long id) {
		ImeiControlFileEntity entity = this.imeiControlFileRepository.findById(id).orElse(null);
		if (entity == null) {
			throw new BadRequestException(String.format(Constants.RECORDNOTFOUNDID, id));
		}
		this.imeiControlFileRepository.delete(entity);
	}

}
