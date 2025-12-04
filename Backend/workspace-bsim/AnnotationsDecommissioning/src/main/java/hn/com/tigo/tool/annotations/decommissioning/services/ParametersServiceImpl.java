package hn.com.tigo.tool.annotations.decommissioning.services;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import hn.com.tigo.tool.annotations.decommissioning.entities.ParametersEntity;
import hn.com.tigo.tool.annotations.decommissioning.repositories.IConfigParametersAnnotationRepository;
import hn.com.tigo.tool.annotations.services.decommissioning.interfaces.IParametersService;

@Service
public class ParametersServiceImpl implements IParametersService {

	private final IConfigParametersAnnotationRepository parametersRepository;

	public ParametersServiceImpl(IConfigParametersAnnotationRepository parametersRepository) {
		super();
	    System.out.println("ParametersServiceImpl constructor called with repository: " + parametersRepository);
		this.parametersRepository = parametersRepository;
	}

	@Override
	public HashMap<String, String> listAllParam() {
		List<ParametersEntity> allParameters = parametersRepository.findAll();
		return allParameters.stream().collect(Collectors.toMap(ParametersEntity::getName, ParametersEntity::getValue,
				(oldValue, newValue) -> oldValue, HashMap::new));
	}

}
