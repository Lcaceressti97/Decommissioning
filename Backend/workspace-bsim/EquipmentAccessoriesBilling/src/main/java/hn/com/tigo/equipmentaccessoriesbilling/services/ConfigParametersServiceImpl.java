package hn.com.tigo.equipmentaccessoriesbilling.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentaccessoriesbilling.entities.ConfigParametersEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.ConfigParametersModel;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IConfigParametersRepository;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IConfigParametersService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.Constants;

@Service
public class ConfigParametersServiceImpl implements IConfigParametersService {

	private final IConfigParametersRepository configParametersRepository;

	public ConfigParametersServiceImpl(IConfigParametersRepository configParametersRepository) {
		this.configParametersRepository = configParametersRepository;
	}

	@Override
	public List<ConfigParametersModel> getAll() {
		List<ConfigParametersEntity> entities = this.configParametersRepository
				.findAll(Sort.by(Sort.Direction.DESC, "created"));
		return entities.stream().map(ConfigParametersEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public List<ConfigParametersModel> getByIdApplication(Long idApplication) {
		List<ConfigParametersEntity> entities = configParametersRepository.findByIdApplication(idApplication);

		if (entities.isEmpty()) {
			throw new BadRequestException(String.format(Constants.ERROR_ID_APP, idApplication.toString()));
		}

		return entities.stream().map(ConfigParametersEntity::entityToModel).collect(Collectors.toList());
	}
	
	@Override
	public ConfigParametersModel getByName(String name) {
		ConfigParametersEntity entity = configParametersRepository.findByParameterName(name);
		if (entity == null) {
			throw new BadRequestException(String.format(Constants.ERROR_PARAMETER_NAME, name.toString()));
		}
		ConfigParametersModel model = entity.entityToModel();
		return model;
	}

}
