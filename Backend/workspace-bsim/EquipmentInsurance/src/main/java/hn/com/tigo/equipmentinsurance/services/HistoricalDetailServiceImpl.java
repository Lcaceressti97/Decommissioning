package hn.com.tigo.equipmentinsurance.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentinsurance.entities.HistoricalDetailEntity;
import hn.com.tigo.equipmentinsurance.models.HistoricalDetailModel;
import hn.com.tigo.equipmentinsurance.repositories.IHistoricalDetailRepository;
import hn.com.tigo.equipmentinsurance.services.interfaces.IHistoricalDetailService;
import hn.com.tigo.equipmentinsurance.utils.Constants;

@Service
public class HistoricalDetailServiceImpl implements IHistoricalDetailService {

	private final IHistoricalDetailRepository historicalDetailRepository;

	public HistoricalDetailServiceImpl(IHistoricalDetailRepository historicalDetailRepository) {
		this.historicalDetailRepository = historicalDetailRepository;
	}

	@Override
	public List<HistoricalDetailModel> getHistoricalByEsnImei(String esnImei) {
		List<HistoricalDetailEntity> entities;
		entities = this.historicalDetailRepository.getHistoricalByEsnImei(esnImei);
		if (entities.isEmpty())
			throw new BadRequestException(String.format(Constants.RECORDNOTFOUNDIMEI, esnImei));
		return entities.stream().map(HistoricalDetailEntity::entityToModel).collect(Collectors.toList());
	}
}
