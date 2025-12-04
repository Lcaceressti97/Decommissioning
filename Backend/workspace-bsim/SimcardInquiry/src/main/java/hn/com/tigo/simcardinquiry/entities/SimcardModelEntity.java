package hn.com.tigo.simcardinquiry.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.simcardinquiry.models.SimcardModelModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "SIMCARD_MODEL")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimcardModelEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "SIM_MODEL")
	private String simModel;

	@Column(name = "MODEL_DESCRIPTION", length = 150)
	private String modelDescription;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "CREATED_DATE")
	private LocalDateTime createdDate;

	public SimcardModelModel entityToModel() {
		SimcardModelModel model = new SimcardModelModel();
		model.setId(this.getId());
		model.setSimModel(this.getSimModel());
		model.setModelDescription(this.getModelDescription());
		model.setStatus(this.getStatus());
		model.setCreatedDate(this.getCreatedDate());
		return model;
	}
}
