package hn.com.tigo.comodatos.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.comodatos.models.ConfigParametersModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "CMD_CONFIG_PARAMETERS")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigParametersEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID", nullable = false, updatable = false)
	private Long id;
	
	@Column(name = "ID_APPLICATION")
	private Long idApplication;

	@Column(name = "PARAMETER_NAME")
	private String parameterName;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "VALUE")
	private String value;
	
	@Column(name = "CREATED")
	private LocalDateTime created;
	
	/**
	 * Método que mapea la entidad con el dto "Modelo"
	 * 
	 * @return
	 */
	public ConfigParametersModel entityToModel() {
		ConfigParametersModel model = new ConfigParametersModel();
		model.setId(this.getId());
		model.setIdApplication(this.getIdApplication());
		model.setParameterName(this.getParameterName());
		model.setDescription(this.getDescription());
		model.setValue(this.getValue());
		model.setCreated(this.getCreated());
		return model;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdApplication() {
		return idApplication;
	}

	public void setIdApplication(Long idApplication) {
		this.idApplication = idApplication;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
