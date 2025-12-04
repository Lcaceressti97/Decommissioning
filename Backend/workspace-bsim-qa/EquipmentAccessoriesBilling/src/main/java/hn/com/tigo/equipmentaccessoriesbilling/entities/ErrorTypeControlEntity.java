package hn.com.tigo.equipmentaccessoriesbilling.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import hn.com.tigo.equipmentaccessoriesbilling.models.ErrorTypeControlModel;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "MEA_ERROR_TYPE_CONTROL")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorTypeControlEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "TYPE_ERROR")
	private Long typeError;

	@Column(name = "DESCRIPTION", length = 100)
	private String description;

	@Column(name = "CREATED", nullable = false)
	private LocalDateTime created;

	public ErrorTypeControlModel entityToModel() {
		ErrorTypeControlModel model = new ErrorTypeControlModel();
		model.setId(this.getId());
		model.setTypeError(this.getTypeError());
		model.setDescription(this.getDescription());
		model.setCreated(this.getCreated());
		return model;
	}
}
