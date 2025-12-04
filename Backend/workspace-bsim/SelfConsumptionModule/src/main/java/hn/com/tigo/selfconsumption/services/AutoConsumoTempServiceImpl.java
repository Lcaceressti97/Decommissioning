package hn.com.tigo.selfconsumption.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hn.com.tigo.selfconsumption.entities.AutoConsumoTempEntity;
import hn.com.tigo.selfconsumption.models.AutoConsumoTempModel;
import hn.com.tigo.selfconsumption.repositories.IAutoConsumoTempRepository;
import hn.com.tigo.selfconsumption.services.interfaces.IAutoConsumoTempService;
import hn.com.tigo.selfconsumption.utils.Constants;

import javax.ws.rs.BadRequestException;

@Service
public class AutoConsumoTempServiceImpl implements IAutoConsumoTempService {

	private final IAutoConsumoTempRepository autoConsumoTempRepository;

	public AutoConsumoTempServiceImpl(IAutoConsumoTempRepository autoConsumoTempRepository) {
		super();
		this.autoConsumoTempRepository = autoConsumoTempRepository;
	}

	@Override
	public List<AutoConsumoTempModel> getAllAutoConsumoTemp() {
		List<AutoConsumoTempEntity> entities = this.autoConsumoTempRepository
				.findAll(Sort.by(Sort.Direction.DESC, "created"));
		return entities.stream().map(AutoConsumoTempEntity::entityToModel).collect(Collectors.toList());

	}

	@Override
	public List<AutoConsumoTempModel> findAutoConsumoTempByFilter(int type, String value) {
		List<AutoConsumoTempEntity> entities;
		entities = this.autoConsumoTempRepository.findByFilter(type, value);
		return entities.stream().map(AutoConsumoTempEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public List<AutoConsumoTempModel> getAutoConsumoTempByDateRange(Optional<LocalDate> startDate,
			Optional<LocalDate> endDate) {
		if (startDate == null || endDate == null) {
			throw new IllegalArgumentException(Constants.ERROR_INVALID_DATE);
		}
		LocalDate startDateTime = startDate.get();
		LocalDate endDateTime = endDate.get().plusDays(1);
		List<AutoConsumoTempEntity> entities = autoConsumoTempRepository.getAutoConsumoTempByDateRange(startDateTime,
				endDateTime);
		return entities.stream().map(AutoConsumoTempEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public AutoConsumoTempModel getAutoConsumoTempById(String id) {
		AutoConsumoTempEntity entity = this.autoConsumoTempRepository.findById(id).orElse(null);

		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

		return entity.entityToModel();
	}

}
