package hn.com.tigo.equipmentaccessoriesbilling.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentaccessoriesbilling.entities.CancelReasonEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.CancelReasonModel;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.ICancelReasonRepository;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.ICancelReasonService;

@Service
public class CancelReasonServiceImpl implements ICancelReasonService{

	private final ICancelReasonRepository cancelReasonRepository;
	
	public CancelReasonServiceImpl(ICancelReasonRepository cancelReasonRepository) {
		super();
		this.cancelReasonRepository = cancelReasonRepository;
	}

	@Override
	public List<CancelReasonModel> getAllCancelReason() {
		List<CancelReasonEntity> entities = this.cancelReasonRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
		return entities.stream().map(CancelReasonEntity::entityToModel).collect(Collectors.toList());

	}

}
