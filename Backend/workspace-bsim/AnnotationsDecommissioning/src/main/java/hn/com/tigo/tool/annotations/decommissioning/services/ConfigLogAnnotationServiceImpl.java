package hn.com.tigo.tool.annotations.decommissioning.services;

import org.springframework.stereotype.Service;

import hn.com.tigo.tool.annotations.decommissioning.entities.ConfigLogAnnotationEntity;
import hn.com.tigo.tool.annotations.decommissioning.repositories.IConfigLogAnnotationRepository;
import hn.com.tigo.tool.annotations.services.decommissioning.interfaces.IConfigLogAnnotationService;

@Service
public class ConfigLogAnnotationServiceImpl implements IConfigLogAnnotationService {

	private final IConfigLogAnnotationRepository configLogAnnotationRepository;

	public ConfigLogAnnotationServiceImpl(IConfigLogAnnotationRepository configLogAnnotationRepository) {
		super();
		this.configLogAnnotationRepository = configLogAnnotationRepository;
	}

	@Override
	public ConfigLogAnnotationEntity findByProjectAndMethodContaining(String project, String method) {
		return configLogAnnotationRepository.findByProjectAndMethodContaining(project, method);
	}

}
