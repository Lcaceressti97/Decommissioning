package hn.com.tigo.equipmentaccessoriesbilling.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;


import hn.com.tigo.equipmentaccessoriesbilling.models.ControlCancellationModel;

@Entity
@Table(name = "MEA_CONTROL_CANCELLATIONS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ControlCancellationEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false, updatable = false)
	private Long id;

	@Column(name = "ID_PREFECTURE")
	private Long idPrefecture;

	@Column(name = "TYPE_CANCELLATION")
	private Long typeCancellation;

	@Lob
	@Column(name = "DESCRIPTION", columnDefinition = "CLOB")
	private String description;

	@Column(name = "USER_CREATE", length = 50)
	private String userCreate;

	@Column(name = "CREATED", nullable = false)
	private LocalDateTime created;

	public ControlCancellationModel entityToModel() {
		ControlCancellationModel model = new ControlCancellationModel();
		model.setId(this.getId());
		model.setIdPrefecture(this.getIdPrefecture());
		model.setTypeCancellation(this.getTypeCancellation());
		model.setDescription(this.getDescription());
		model.setUserCreate(this.getUserCreate());
		model.setCreated(this.getCreated());
		return model;
	}
}
