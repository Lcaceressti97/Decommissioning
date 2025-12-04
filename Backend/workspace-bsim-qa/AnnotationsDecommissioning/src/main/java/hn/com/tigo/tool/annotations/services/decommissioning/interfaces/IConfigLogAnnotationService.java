package hn.com.tigo.tool.annotations.services.decommissioning.interfaces;

import hn.com.tigo.tool.annotations.decommissioning.entities.ConfigLogAnnotationEntity;

public interface IConfigLogAnnotationService {

	ConfigLogAnnotationEntity findByProjectAndMethodContaining(String project, String method);
}
