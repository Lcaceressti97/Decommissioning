package hn.com.tigo.equipmentinsurance.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentinsurance.entities.ReasonsEntity;
import hn.com.tigo.equipmentinsurance.models.ReasonsModel;
import hn.com.tigo.equipmentinsurance.repositories.IReasonsRepository;
import hn.com.tigo.equipmentinsurance.services.interfaces.IReasonsService;
import hn.com.tigo.equipmentinsurance.utils.Constants;

@Service
public class ReasonsServiceImpl implements IReasonsService {

	private final IReasonsRepository reasonsRepository;

	public ReasonsServiceImpl(IReasonsRepository reasonsRepository) {
		super();
		this.reasonsRepository = reasonsRepository;
	}

	@Override
	public List<ReasonsModel> getAllReasons() {
		List<ReasonsEntity> entities = this.reasonsRepository.findAll();
		return entities.stream().map(ReasonsEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public ReasonsModel getReasonById(Long id) {
		ReasonsEntity entity = this.reasonsRepository.findById(id).orElse(null);

		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

		return entity.entityToModel();
	}

	@Override
	public void addReason(ReasonsModel model) {
		ReasonsEntity entity = new ReasonsEntity();
		entity.setId(-1L);
		entity.setReason(model.getReason());
		entity.setDescription(model.getDescription());
		entity.setStatus(model.getStatus());
		entity.setCreatedDate(LocalDateTime.now());

		this.reasonsRepository.save(entity);

	}

	@Override
	public void updateReason(Long id, ReasonsModel model) {
		ReasonsEntity entity = this.reasonsRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

		entity.setReason(model.getReason());
		entity.setDescription(model.getDescription());
		entity.setStatus(model.getStatus());
		this.reasonsRepository.save(entity);

	}

	@Override
	public void deleteReason(Long id) {
		ReasonsEntity entity = this.reasonsRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_DELETE, id));

		this.reasonsRepository.delete(entity);

	}

}
