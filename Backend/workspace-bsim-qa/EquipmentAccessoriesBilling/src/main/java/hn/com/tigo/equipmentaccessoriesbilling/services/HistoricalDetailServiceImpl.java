package hn.com.tigo.equipmentaccessoriesbilling.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentaccessoriesbilling.entities.HistoricalDetailEntity;
import hn.com.tigo.equipmentaccessoriesbilling.models.HistoricalDetailModel;
import hn.com.tigo.equipmentaccessoriesbilling.repositories.IHistoricalDetailRepository;
import hn.com.tigo.equipmentaccessoriesbilling.services.interfaces.IHistoricalDetailService;
import hn.com.tigo.equipmentaccessoriesbilling.utils.Constants;

@Service
public class HistoricalDetailServiceImpl implements IHistoricalDetailService {

	private final IHistoricalDetailRepository historicalDetailRepository;

	public HistoricalDetailServiceImpl(IHistoricalDetailRepository historicalDetailRepository) {
		this.historicalDetailRepository = historicalDetailRepository;
	}

	@Override
	public List<HistoricalDetailModel> getHistoricalDetailByPhone(String phone) {
		List<HistoricalDetailEntity> entities;
		entities = this.historicalDetailRepository.getHistoricalDetailByPhone(phone);
		if (entities.isEmpty())
			throw new BadRequestException(String.format(Constants.ERROR_NOT_FOUND_PHONE, phone));
		return entities.stream().map(HistoricalDetailEntity::entityToModel).collect(Collectors.toList());
	}

}
