package hn.com.tigo.simcardinquiry.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.simcardinquiry.models.SimcardTypeModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "SIMCARD_TYPE")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimcardTypeEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "TYPE_NUMBER")
	private String typeNumber;

	@Column(name = "TYPE_DESCRIPTION", length = 150)
	private String typeDescription;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "CREATED_DATE")
	private LocalDateTime createdDate;

	public SimcardTypeModel entityToModel() {
		SimcardTypeModel model = new SimcardTypeModel();
		model.setId(this.getId());
		model.setTypeNumber(this.getTypeNumber());
		model.setTypeDescription(this.getTypeDescription());
		model.setStatus(this.getStatus());
		model.setCreatedDate(this.getCreatedDate());
		return model;
	}
}
