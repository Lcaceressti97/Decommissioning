package hn.com.tigo.comodatos.service;

import java.util.List;
import java.util.stream.Collectors;



import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hn.com.tigo.comodatos.entities.ConfigParametersEntity;
import hn.com.tigo.comodatos.exceptions.BadRequestException;
import hn.com.tigo.comodatos.models.ConfigParametersModel;
import hn.com.tigo.comodatos.repositories.IConfigParametersRepository;
import hn.com.tigo.comodatos.services.interfaces.IConfigParametersService;

@Service
public class ConfigParametersServiceImpl implements IConfigParametersService {

	// Props
	private IConfigParametersRepository configParametersRepository;

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
			throw new BadRequestException("No records found for the idApplication: " + idApplication);
		}

		return entities.stream().map(ConfigParametersEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public void update(Long id, ConfigParametersModel model) {

			ConfigParametersEntity entity = this.configParametersRepository.findById(id).orElse(null);
			
			if(entity==null) {
				
				throw new BadRequestException("Error update, no records found for the id: " + id);
			}else {
				
				entity.setDescription(model.getDescription());
				entity.setParameterName(model.getParameterName());
				entity.setValue(model.getValue());
				
				this.configParametersRepository.save(entity);
				
			}
			
		
	}

}
