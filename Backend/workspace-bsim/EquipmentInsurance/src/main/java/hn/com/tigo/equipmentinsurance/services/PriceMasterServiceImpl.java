package hn.com.tigo.equipmentinsurance.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentinsurance.entities.PriceMasterEntity;
import hn.com.tigo.equipmentinsurance.models.PriceMasterModel;
import hn.com.tigo.equipmentinsurance.repositories.IPriceMasterRepository;
import hn.com.tigo.equipmentinsurance.services.interfaces.IPriceMasterService;
import hn.com.tigo.equipmentinsurance.utils.Constants;

@Service
public class PriceMasterServiceImpl implements IPriceMasterService {

	private final IPriceMasterRepository priceMasterRepository;

	public PriceMasterServiceImpl(IPriceMasterRepository priceMasterRepository) {
		super();
		this.priceMasterRepository = priceMasterRepository;
	}

	@Override
	public List<PriceMasterModel> getPriceMaster() {
		List<PriceMasterEntity> entities = this.priceMasterRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
		return entities.stream().map(PriceMasterEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public List<PriceMasterModel> findByInventoryTypeAndModel(String inventoryType, String model) {
		List<PriceMasterEntity> entities = this.priceMasterRepository.findByInventoryTypeAndModel(inventoryType, model);
		return entities.stream().map(PriceMasterEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public PriceMasterModel getPriceMasterById(Long id) {
		PriceMasterEntity entity = this.priceMasterRepository.findById(id).orElse(null);

		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

		return entity.entityToModel();
	}

	@Override
	public void createdPriceMaster(PriceMasterModel model) {
		PriceMasterEntity entity = new PriceMasterEntity();

		entity.setId(-1L);
		entity.setInventoryType(model.getInventoryType());
		entity.setModel(model.getModel());
		entity.setBaseCost(model.getBaseCost());
		entity.setFactorCode(model.getFactorCode());
		entity.setPrice(model.getPrice());
		entity.setUserCreated(model.getUserCreated());
		entity.setScreen(model.getScreen());
		entity.setCreatedDate(LocalDateTime.now());
		entity.setCurrency(model.getCurrency());
		entity.setLempirasPrice(model.getLempirasPrice());
		entity.setTemporaryCost(model.getTemporaryCost());
		this.priceMasterRepository.save(entity);

	}

	@Override
	public void updatePriceMaster(Long id, PriceMasterModel model) {
		PriceMasterEntity entity = this.priceMasterRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

		entity.setInventoryType(model.getInventoryType());
		entity.setModel(model.getModel());
		entity.setBaseCost(model.getBaseCost());
		entity.setFactorCode(model.getFactorCode());
		entity.setPrice(model.getPrice());
		entity.setUserCreated(model.getUserCreated());
		entity.setScreen(model.getScreen());
		entity.setCurrency(model.getCurrency());
		entity.setLempirasPrice(model.getLempirasPrice());
		entity.setTemporaryCost(model.getTemporaryCost());
		this.priceMasterRepository.save(entity);
	}

	@Override
	public void deletePriceMaster(Long id) {
		PriceMasterEntity entity = this.priceMasterRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_DELETE, id));

		this.priceMasterRepository.delete(entity);

	}

}
