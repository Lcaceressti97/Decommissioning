package hn.com.tigo.equipmentblacklist.services;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hn.com.tigo.equipmentblacklist.entities.HistoricalDetailEntity;
import hn.com.tigo.equipmentblacklist.exceptions.BadRequestException;
import hn.com.tigo.equipmentblacklist.models.HistoricalDetailModel;
import hn.com.tigo.equipmentblacklist.repositories.IHistoricalDetailRepository;
import hn.com.tigo.equipmentblacklist.services.interfaces.IHistoricalDetailService;
import hn.com.tigo.equipmentblacklist.utils.Constants;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoricalDetailServiceImpl implements IHistoricalDetailService {

	private final IHistoricalDetailRepository historicalDetailRepository;

	public HistoricalDetailServiceImpl(IHistoricalDetailRepository historicalDetailRepository) {
		this.historicalDetailRepository = historicalDetailRepository;
	}

	@Override
	public List<HistoricalDetailModel> getAll() {
		List<HistoricalDetailEntity> entities = this.historicalDetailRepository
				.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));
		return entities.stream().map(HistoricalDetailEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public List<HistoricalDetailModel> getHistoricalByEsnImei(String esnImei) {
		List<HistoricalDetailEntity> entities;
		entities = this.historicalDetailRepository.getHistoricalByEsnImei(esnImei);
		if (entities.isEmpty())
			throw new BadRequestException(String.format(Constants.RECORDNOTFOUNDIMEI, esnImei));
		return entities.stream().map(HistoricalDetailEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public HistoricalDetailModel getById(Long id) {
		HistoricalDetailEntity entity = this.historicalDetailRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.RECORDNOTFOUNDID, id));
		return entity.entityToModel();
	}

	@Override
	public void delete(Long id) {
		HistoricalDetailEntity entity = this.historicalDetailRepository.findById(id).orElse(null);
		if (entity == null)
			throw new BadRequestException(String.format(Constants.RECORDNOTFOUNDID, id));

		this.historicalDetailRepository.delete(entity);
	}

}
