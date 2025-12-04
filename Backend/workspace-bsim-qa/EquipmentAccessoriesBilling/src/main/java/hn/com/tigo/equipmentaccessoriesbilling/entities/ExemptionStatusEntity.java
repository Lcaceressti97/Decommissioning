package hn.com.tigo.equipmentaccessoriesbilling.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.equipmentaccessoriesbilling.models.ExemptionStatusModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "MEA_EXEMPTION_STATUS")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExemptionStatusEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "ID_STATUS")
	private Long idStatus;

	@Column(name = "NAME_STATUS", length = 50)
	private String nameStatus;

	@Column(name = "CREATED")
	private LocalDateTime created;

	public ExemptionStatusModel entityToModel() {
		ExemptionStatusModel model = new ExemptionStatusModel();
		model.setId(this.getId());
		model.setIdStatus(this.getIdStatus());
		model.setNameStatus(this.getNameStatus());
		model.setCreated(this.getCreated());
		return model;
	}

}
