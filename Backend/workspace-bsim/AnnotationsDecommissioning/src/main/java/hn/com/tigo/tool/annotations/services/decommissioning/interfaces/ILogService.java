package hn.com.tigo.tool.annotations.services.decommissioning.interfaces;

import hn.com.tigo.tool.annotations.decommissioning.entities.LogEntity;
import hn.com.tigo.tool.annotations.decommissioning.models.LogModel;

public interface ILogService {

	void saveLog(LogModel model);

	long insertLog(LogEntity log);
}
