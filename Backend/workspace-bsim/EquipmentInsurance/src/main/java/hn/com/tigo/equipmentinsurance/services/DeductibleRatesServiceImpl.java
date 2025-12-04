package hn.com.tigo.equipmentinsurance.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentinsurance.entities.DeductibleRatesEntity;
import hn.com.tigo.equipmentinsurance.entities.ReasonsEntity;
import hn.com.tigo.equipmentinsurance.models.CalculateDeductibleRequest;
import hn.com.tigo.equipmentinsurance.models.CalculateDeductibleResponse;
import hn.com.tigo.equipmentinsurance.models.DeductibleRatesModel;
import hn.com.tigo.equipmentinsurance.repositories.IDeductibleRatesRepository;
import hn.com.tigo.equipmentinsurance.repositories.IReasonsRepository;
import hn.com.tigo.equipmentinsurance.services.interfaces.IDeductibleRatesService;
import hn.com.tigo.equipmentinsurance.utils.Constants;

@Service
public class DeductibleRatesServiceImpl implements IDeductibleRatesService {

	private final IDeductibleRatesRepository deductibleRatesRepository;
	private final IReasonsRepository reasonsRepository;

	public DeductibleRatesServiceImpl(IDeductibleRatesRepository deductibleRatesRepository,
			IReasonsRepository reasonsRepository) {
		super();
		this.deductibleRatesRepository = deductibleRatesRepository;
		this.reasonsRepository = reasonsRepository;
	}

	@Override
	public List<DeductibleRatesModel> getAllDeductibleRates() {
		List<DeductibleRatesEntity> entities = this.deductibleRatesRepository
				.findAll(Sort.by(Sort.Direction.DESC, "id"));
		return entities.stream().map(DeductibleRatesEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public List<DeductibleRatesModel> getDeductibleRatesByModel(String model) {
		List<DeductibleRatesEntity> entities = deductibleRatesRepository.getDeductibleRatesByModel(model);

		if (entities.isEmpty()) {
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_MODEL, model));
		}

		return entities.stream().map(DeductibleRatesEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public CalculateDeductibleResponse getCalculateDeductible(CalculateDeductibleRequest request) {
		
	    ReasonsEntity reason = this.reasonsRepository.findById(request.getReasonId())
	            .orElseThrow(() -> new BadRequestException(String.format(Constants.ERROR_REASON)));
	   
		DeductibleRatesEntity rates = this.deductibleRatesRepository.getDeductibleRatesByModelAndReason(request.getModel(),
				reason);
		
		CalculateDeductibleResponse response = new CalculateDeductibleResponse();
		
		if (rates == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_MODEL, request.getModel()));

		Double deductibleRate;

		switch (request.getQuantity()) {
		case 1:
			deductibleRate = Double.parseDouble(rates.getFirstClaim());
			break;
		case 2:
			deductibleRate = Double.parseDouble(rates.getSecondClaim());
			break;
		case 3:
			deductibleRate = Double.parseDouble(rates.getThirdClaim());
			break;
		default:
			throw new BadRequestException(String.format(Constants.ERROR_QUANTITY_DEDUCTIBLE));
		}

	    // Cálculo del total deducible
	    BigDecimal price = BigDecimal.valueOf(request.getPrice());
	    BigDecimal rate = BigDecimal.valueOf(deductibleRate);
	    BigDecimal totalDeductible = price.multiply(rate).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

	    response.setTotalDeductible(totalDeductible.doubleValue());

		return response;
	}

	@Override
	public DeductibleRatesModel getDeductibleRatesById(Long id) {
		DeductibleRatesEntity entity = this.deductibleRatesRepository.findById(id).orElse(null);

		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

		return entity.entityToModel();
	}

	@Override
	public void addDeductibleRates(DeductibleRatesModel model) {

		DeductibleRatesEntity entity = new DeductibleRatesEntity();

		ReasonsEntity reason = reasonsRepository.findById(model.getReason())
				.orElseThrow(() -> new BadRequestException(String.format("Reason %d not valid ", model.getReason())));

		entity.setId(-1L);
		entity.setModel(model.getModel());
		entity.setDescription(model.getDescription());
		entity.setFirstClaim(model.getFirstClaim());
		entity.setSecondClaim(model.getSecondClaim());
		entity.setThirdClaim(model.getThirdClaim());
		entity.setReason(reason);
		entity.setStatus(model.getStatus());
		entity.setCreated(LocalDateTime.now());

		this.deductibleRatesRepository.save(entity);

	}

	@Override
	public void updateDeductibleRates(Long id, DeductibleRatesModel model) {

		DeductibleRatesEntity entity = this.deductibleRatesRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

		ReasonsEntity reason = reasonsRepository.findById(model.getReason())
				.orElseThrow(() -> new BadRequestException(String.format("Reason %d not valid ", model.getReason())));

		entity.setModel(model.getModel());
		entity.setDescription(model.getDescription());
		entity.setFirstClaim(model.getFirstClaim());
		entity.setSecondClaim(model.getSecondClaim());
		entity.setThirdClaim(model.getThirdClaim());
		entity.setReason(reason);
		entity.setStatus(model.getStatus());
		this.deductibleRatesRepository.save(entity);

	}

	@Override
	public void deleteDeductibleRates(Long id) {
		DeductibleRatesEntity entity = this.deductibleRatesRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FINDING_AN_ID_IN_DELETE, id));

		this.deductibleRatesRepository.delete(entity);
	}

}
