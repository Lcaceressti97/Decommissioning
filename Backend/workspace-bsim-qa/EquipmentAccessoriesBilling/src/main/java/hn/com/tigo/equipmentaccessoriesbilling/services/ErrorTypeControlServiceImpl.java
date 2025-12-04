package hn.com.tigo.equipmentaccessoriesbilling.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentaccessoriesbilling.entities.ErrorTypeControlEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.ErrorTypeControlModel;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IErrorTypeControlRepository;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IErrorTypeControlService;

@Service
public class ErrorTypeControlServiceImpl implements IErrorTypeControlService {

	private final IErrorTypeControlRepository errorTypeControlRepository;

	public ErrorTypeControlServiceImpl(IErrorTypeControlRepository errorTypeControlRepository) {
		super();
		this.errorTypeControlRepository = errorTypeControlRepository;
	}

	@Override
	public List<ErrorTypeControlModel> getAll() {
		List<ErrorTypeControlEntity> entities = this.errorTypeControlRepository
				.findAll(Sort.by(Sort.Direction.DESC, "created"));
		return entities.stream().map(ErrorTypeControlEntity::entityToModel).collect(Collectors.toList());
	}

}
