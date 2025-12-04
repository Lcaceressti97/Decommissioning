package hn.com.tigo.selfconsumption.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hn.com.tigo.selfconsumption.entities.ParametersAutoconsumoEntity;
import hn.com.tigo.selfconsumption.models.ParametersAutoconsumoModel;
import hn.com.tigo.selfconsumption.repositories.IParametersAutoconsumoRepository;
import hn.com.tigo.selfconsumption.services.interfaces.IParametersAutoconsumoService;
import hn.com.tigo.selfconsumption.utils.Constants;
import javax.ws.rs.BadRequestException;

@Service
public class ParametersAutoconsumoServiceImpl implements IParametersAutoconsumoService {

	private final IParametersAutoconsumoRepository parametersAutoconsumoRepository;

	public ParametersAutoconsumoServiceImpl(IParametersAutoconsumoRepository parametersAutoconsumoRepository) {
		super();
		this.parametersAutoconsumoRepository = parametersAutoconsumoRepository;
	}

	@Override
	public List<ParametersAutoconsumoModel> getAllParametersAutoconsumo() {
		List<ParametersAutoconsumoEntity> entities = this.parametersAutoconsumoRepository
				.findAll(Sort.by(Sort.Direction.DESC, "id"));
		return entities.stream().map(ParametersAutoconsumoEntity::entityToModel).collect(Collectors.toList());

	}

	@Override
	public void updateParametersAutoconsumo(Long id, ParametersAutoconsumoModel model) {
		try {
			ParametersAutoconsumoEntity entity = this.parametersAutoconsumoRepository.findById(id).orElse(null);
			if (entity == null)
				throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_RECORD, id));

			entity.setName(model.getName());
			entity.setValue(model.getValue());
			entity.setDescription(model.getDescription());
			entity.setStatus(1L);
			entity.setCreatedDate(LocalDateTime.now());
			this.parametersAutoconsumoRepository.save(entity);
		} catch (BadRequestException e) {
			throw e;

		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public List<ParametersAutoconsumoModel> getByIdApplication(Long idApplication) {
		List<ParametersAutoconsumoEntity> entities = parametersAutoconsumoRepository.findByIdApplication(idApplication);

		if (entities.isEmpty()) {
			throw new BadRequestException(String.format(Constants.ERROR_ID_APP, idApplication.toString()));
		}

		return entities.stream().map(ParametersAutoconsumoEntity::entityToModel).collect(Collectors.toList());
	}

}
