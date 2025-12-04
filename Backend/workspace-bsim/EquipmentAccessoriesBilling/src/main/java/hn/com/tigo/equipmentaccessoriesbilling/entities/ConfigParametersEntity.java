package hn.com.tigo.equipmentaccessoriesbilling.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.equipmentaccessoriesbilling.models.ConfigParametersModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "MEA_CONFIG_PARAMETERS")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigParametersEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false, updatable = false)
	private Long id;
	
	@Column(name = "ID_APPLICATION")
	private Long idApplication;

	@Column(name = "PARAMETER_TYPE")
	private String parameterType;

	@Column(name = "PARAMETER_NAME")
	private String parameterName;

	@Column(name = "PARAMETER_DESCRIPTION")
	private String parameterDescription;

	@Column(name = "PARAMETER_VALUE")
	private String parameterValue;

	@Column(name = "STATE_CODE")
	private Long stateCode;

	@Column(name = "CREATED")
	private LocalDateTime created;

	public ConfigParametersModel entityToModel() {
		ConfigParametersModel model = new ConfigParametersModel();
		model.setId(this.getId());
		model.setIdApplication(this.getIdApplication());
		model.setParameterType(this.getParameterType());
		model.setParameterName(this.getParameterName());
		model.setParameterDescription(this.getParameterDescription());
		model.setParameterValue(this.getParameterValue());
		model.setStateCode(this.getStateCode());
		model.setCreated(this.getCreated());
		return model;
	}

}
