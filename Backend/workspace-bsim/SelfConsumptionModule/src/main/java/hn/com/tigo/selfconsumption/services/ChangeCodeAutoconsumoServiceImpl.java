package hn.com.tigo.selfconsumption.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hn.com.tigo.selfconsumption.entities.ChangeCodeAutoconsumoEntity;
import hn.com.tigo.selfconsumption.models.ChangeCodeAutoconsumoModel;
import hn.com.tigo.selfconsumption.repositories.IChangeCodeAutoconsumoRepository;
import hn.com.tigo.selfconsumption.services.interfaces.IChangeCodeAutoconsumoService;
import hn.com.tigo.selfconsumption.utils.Constants;
import javax.ws.rs.BadRequestException;

@Service
public class ChangeCodeAutoconsumoServiceImpl implements IChangeCodeAutoconsumoService {

	private final IChangeCodeAutoconsumoRepository changeCodeAutoconsumoRepository;

	public ChangeCodeAutoconsumoServiceImpl(IChangeCodeAutoconsumoRepository changeCodeAutoconsumoRepository) {
		super();
		this.changeCodeAutoconsumoRepository = changeCodeAutoconsumoRepository;
	}

	@Override
	public List<ChangeCodeAutoconsumoModel> getAllChangeCodeAutoconsumo() {
		List<ChangeCodeAutoconsumoEntity> entities = this.changeCodeAutoconsumoRepository
				.findAll(Sort.by(Sort.Direction.DESC, "id"));
		return entities.stream().map(ChangeCodeAutoconsumoEntity::entityToModel).collect(Collectors.toList());

	}

	@Override
	public ChangeCodeAutoconsumoModel getChangeCodeAutoconsumoById(Long id) {
		ChangeCodeAutoconsumoEntity entity = this.changeCodeAutoconsumoRepository.findById(id).orElse(null);

		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

		return entity.entityToModel();
	}

	@Override
	public Long addChangeCodeAutoconsumo(ChangeCodeAutoconsumoModel model) {
	    try {
	        ChangeCodeAutoconsumoEntity entity = new ChangeCodeAutoconsumoEntity();
	        entity.setOfferingId(model.getOfferingId());
	        entity.setChargeCode(model.getChargeCode());
	        entity.setItemName(model.getItemName());
	        entity.setUserName(model.getUserName());
	        entity.setStatus(model.getStatus());
	        entity.setCreateDate(LocalDateTime.now());
	        entity = this.changeCodeAutoconsumoRepository.save(entity);
			Long generatedId = entity.getId();
			System.out.println(generatedId);
			return generatedId;

	    } catch (BadRequestException e) {
	        throw e;
	    } catch (Exception e) {
	        throw e;
	    }
	}

	@Override
	public void updateChangeCodeAutoconsumo(Long id, ChangeCodeAutoconsumoModel model) {
		try {
			ChangeCodeAutoconsumoEntity entity = this.changeCodeAutoconsumoRepository.findById(id).orElse(null);
			if (entity == null)
				throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

			entity.setOfferingId(model.getOfferingId());
			entity.setChargeCode(model.getChargeCode());
			entity.setItemName(model.getItemName());
			entity.setUserName(model.getUserName());
			entity.setStatus(model.getStatus());
			this.changeCodeAutoconsumoRepository.save(entity);
		} catch (BadRequestException e) {
			throw e;

		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public void deleteChangeCodeAutoconsumo(Long id) {
		ChangeCodeAutoconsumoEntity entity = this.changeCodeAutoconsumoRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

		this.changeCodeAutoconsumoRepository.delete(entity);
	}

}
