package hn.com.tigo.equipmentinsurance.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentinsurance.entities.ConfigParametersEntity;
import hn.com.tigo.equipmentinsurance.models.ConfigParametersModel;
import hn.com.tigo.equipmentinsurance.repositories.IConfigParametersRepository;
import hn.com.tigo.equipmentinsurance.services.interfaces.IConfigParametersService;
import hn.com.tigo.equipmentinsurance.utils.Constants;

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

}
