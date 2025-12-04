package hn.com.tigo.tool.annotations.decommissioning.services;

import org.springframework.stereotype.Service;

import hn.com.tigo.tool.annotations.decommissioning.entities.LogEntity;
import hn.com.tigo.tool.annotations.decommissioning.models.LogModel;
import hn.com.tigo.tool.annotations.decommissioning.repositories.ILogRepository;
import hn.com.tigo.tool.annotations.services.decommissioning.interfaces.ILogService;

@Service
public class LogServiceImpl implements ILogService {

	private final ILogRepository logRepository;

	public LogServiceImpl(ILogRepository logRepository) {
		super();
		this.logRepository = logRepository;
	}

	@Override
	public long insertLog(LogEntity log) {
		try {
			logRepository.save(log);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public void saveLog(LogModel model) {
		LogEntity entity = new LogEntity();

		entity.setIdApp(model.getIdApp());
		entity.setDataSource(model.getDataSource());
		entity.setStartDate(model.getStartDate());
		entity.setEndDate(model.getEndDate());
		entity.setRequest(model.getRequest());
		entity.setResponse(model.getResponse());
		entity.setMethod(model.getMethod());
		entity.setApiMethod(model.getApiMethod());
		entity.setHttpResponseCode(model.getHttpResponseCode());
		entity.setUri(model.getUri());

		this.logRepository.save(entity);
	}

}
