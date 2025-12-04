package hn.com.tigo.selfconsumption.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;

import hn.com.tigo.selfconsumption.entities.ChangeCodeHistoricalEntity;
import hn.com.tigo.selfconsumption.models.ChangeCodeHistoricalModel;
import hn.com.tigo.selfconsumption.repositories.IChangeCodeHistoricalRepository;
import hn.com.tigo.selfconsumption.services.interfaces.IChangeCodeHistoricalService;
import hn.com.tigo.selfconsumption.utils.Constants;

@Service
public class ChangeCodeHistoricalServiceImpl implements IChangeCodeHistoricalService {

	private final IChangeCodeHistoricalRepository changeCodeHistoricalRepository;

	public ChangeCodeHistoricalServiceImpl(IChangeCodeHistoricalRepository changeCodeHistoricalRepository) {
		super();
		this.changeCodeHistoricalRepository = changeCodeHistoricalRepository;
	}

	@Override
	public List<ChangeCodeHistoricalModel> getChangeCodeHistoricalById(Long chargeCodeId) {
		List<ChangeCodeHistoricalEntity> entities = this.changeCodeHistoricalRepository
				.getChangeCodeHistoricalByChargeCodeId(chargeCodeId);
		return entities.stream().map(ChangeCodeHistoricalEntity::entityToModel).collect(Collectors.toList());

	}

	@Override
	public void addChangeCodeHistorical(ChangeCodeHistoricalModel model) {
		try {
			ChangeCodeHistoricalEntity entity = new ChangeCodeHistoricalEntity();
			entity.setId(-1L);
			entity.setChargeCodeId(model.getChargeCodeId());
			entity.setChargeCode(model.getChargeCode());
			entity.setItemName(model.getItemName());
			entity.setUserName(model.getUserName());
			entity.setStatus(model.getStatus());
			entity.setCreateDate(LocalDateTime.now());
			entity.setAction(model.getAction());
			this.changeCodeHistoricalRepository.save(entity);
		} catch (BadRequestException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void updateChangeCodeHistorical(Long id, ChangeCodeHistoricalModel model) {
		try {
			ChangeCodeHistoricalEntity entity = this.changeCodeHistoricalRepository.findById(id).orElse(null);
			if (entity == null)
				throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

			entity.setChargeCodeId(model.getChargeCodeId());
			entity.setChargeCode(model.getChargeCode());
			entity.setItemName(model.getItemName());
			entity.setUserName(model.getUserName());
			entity.setStatus(model.getStatus());
			entity.setCreateDate(LocalDateTime.now());
			entity.setAction(model.getAction());
			this.changeCodeHistoricalRepository.save(entity);
		} catch (BadRequestException e) {
			throw e;

		} catch (Exception e) {
			throw e;
		}
	}

}
