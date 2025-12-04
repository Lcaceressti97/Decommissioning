package hn.com.tigo.equipmentinsurance.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentinsurance.entities.PremiumRatesEntity;
import hn.com.tigo.equipmentinsurance.models.PremiumRatesModel;
import hn.com.tigo.equipmentinsurance.repositories.IPremiumRatesRepository;
import hn.com.tigo.equipmentinsurance.services.interfaces.IPremiumRatesServices;
import hn.com.tigo.equipmentinsurance.utils.Constants;

@Service
public class PremiumRatesServicesImpl implements IPremiumRatesServices {

	private final IPremiumRatesRepository premiumRatesRepository;

	public PremiumRatesServicesImpl(IPremiumRatesRepository premiumRatesRepository) {
		super();
		this.premiumRatesRepository = premiumRatesRepository;
	}

	@Override
	public List<PremiumRatesModel> getAll() {
		List<PremiumRatesEntity> entities = this.premiumRatesRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
		return entities.stream().map(PremiumRatesEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public List<PremiumRatesModel> getPremiumRatesByModel(String model) {
		List<PremiumRatesEntity> entities = premiumRatesRepository.getPremiumRatesByModel(model);

		if (entities.isEmpty()) {
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_MODEL, model));
		}

		return entities.stream().map(PremiumRatesEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public PremiumRatesModel getPremiumRatesById(Long id) {
		PremiumRatesEntity entity = this.premiumRatesRepository.findById(id).orElse(null);

		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

		return entity.entityToModel();
	}

	@Override
	public void addPremiumRates(PremiumRatesModel model) {
		PremiumRatesEntity entity = new PremiumRatesEntity();
		entity.setId(-1L);
		entity.setModel(model.getModel());
		entity.setDeviceValue(model.getDeviceValue());
		entity.setMonthlyPremium(model.getMonthlyPremium());
		entity.setStatus(1L);
		entity.setCreated(LocalDateTime.now());

		this.premiumRatesRepository.save(entity);

	}

	@Override
	public void updatePremiumRates(Long id, PremiumRatesModel model) {
		PremiumRatesEntity entity = this.premiumRatesRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

		entity.setModel(model.getModel());
		entity.setModel(model.getModel());
		entity.setDeviceValue(model.getDeviceValue());
		entity.setMonthlyPremium(model.getMonthlyPremium());
		this.premiumRatesRepository.save(entity);
	}

	@Override
	public void deletePremiumRates(Long id) {
		PremiumRatesEntity entity = this.premiumRatesRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_DELETE, id));

		this.premiumRatesRepository.delete(entity);

	}

}
