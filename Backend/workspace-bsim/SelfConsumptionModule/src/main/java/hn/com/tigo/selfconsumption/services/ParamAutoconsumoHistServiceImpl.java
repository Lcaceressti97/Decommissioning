package hn.com.tigo.selfconsumption.services;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import hn.com.tigo.selfconsumption.entities.ParamAutoconsumoHistEntity;
import hn.com.tigo.selfconsumption.models.ParamAutoconsumoHistModel;
import hn.com.tigo.selfconsumption.repositories.IParamAutoconsumoHistRepository;
import hn.com.tigo.selfconsumption.services.interfaces.IParamAutoconsumoHistService;
import javax.ws.rs.BadRequestException;

@Service
public class ParamAutoconsumoHistServiceImpl implements IParamAutoconsumoHistService {

	private final IParamAutoconsumoHistRepository paramAutoconsumoHistRepository;

	public ParamAutoconsumoHistServiceImpl(IParamAutoconsumoHistRepository paramAutoconsumoHistRepository) {
		super();
		this.paramAutoconsumoHistRepository = paramAutoconsumoHistRepository;
	}

	@Override
	public List<ParamAutoconsumoHistModel> getAllParamAutoconsumoHist(Long idParameter) {
		List<ParamAutoconsumoHistEntity> entities = this.paramAutoconsumoHistRepository
				.getParamAutoconsumoHistByIdParameter(idParameter);
		return entities.stream().sorted(Comparator.comparing(ParamAutoconsumoHistEntity::getId).reversed())
				.map(ParamAutoconsumoHistEntity::entityToModel).collect(Collectors.toList());
	}

	@Override
	public void addParamAutoconsumoHist(ParamAutoconsumoHistModel model) {
		try {
			ParamAutoconsumoHistEntity entity = new ParamAutoconsumoHistEntity();
			entity.setId(-1L);
			entity.setIdParameter(model.getIdParameter());
			entity.setName(model.getName());
			entity.setValue(model.getValue());
			entity.setDescription(model.getDescription());
			entity.setUserName(model.getUserName());
			entity.setStatus(model.getStatus());
			entity.setCreateDate(LocalDateTime.now());
			this.paramAutoconsumoHistRepository.save(entity);
		} catch (BadRequestException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}

	}

}
