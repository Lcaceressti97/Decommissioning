package hn.com.tigo.simcardinquiry.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;

import hn.com.tigo.simcardinquiry.entities.ConfigParameterEntity;
import hn.com.tigo.simcardinquiry.models.ConfigParameterModel;
import hn.com.tigo.simcardinquiry.repositories.IConfigParameterRepository;
import hn.com.tigo.simcardinquiry.services.interfaces.IConfigParameterService;
import hn.com.tigo.simcardinquiry.utils.Constants;

@Service
public class ConfigParameterServiceImpl implements IConfigParameterService {

	private final IConfigParameterRepository configParametersRepository;

	public ConfigParameterServiceImpl(IConfigParameterRepository configParametersRepository) {
		this.configParametersRepository = configParametersRepository;
	}

	@Override
	public List<ConfigParameterModel> getByIdApplication(Long idApplication) {
		List<ConfigParameterEntity> entities = configParametersRepository.findByIdApplication(idApplication);

		if (entities.isEmpty()) {
			throw new BadRequestException(String.format(Constants.ERROR_ID_APP, idApplication.toString()));
		}

		return entities.stream().map(ConfigParameterEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public void updateParameter(String parameterValue, ConfigParameterModel model) {

		ConfigParameterEntity entity = this.configParametersRepository.findByParameterValue(parameterValue);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_PARAMETER, parameterValue));

		entity.setParameterName(model.getParameterName());
		this.configParametersRepository.save(entity);

	}

	@Override
	public ConfigParameterModel findByParameterName(String parameterName) {
		ConfigParameterEntity entity = this.configParametersRepository.findByParameterName(parameterName);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_PARAMETER, parameterName));
		return entity.entityToModel();
	}

}
