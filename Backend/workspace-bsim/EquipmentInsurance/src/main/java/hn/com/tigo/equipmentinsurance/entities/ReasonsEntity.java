package hn.com.tigo.equipmentinsurance.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.equipmentinsurance.models.ReasonsModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "SE_REASONS")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReasonsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "REASON", length = 150)
	private String reason;

	@Column(name = "DESCRIPTION", length = 250)
	private String description;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "CREATED_DATE")
	private LocalDateTime createdDate;

	public ReasonsModel entityToModel() {
		ReasonsModel model = new ReasonsModel();
		model.setId(this.getId());
		model.setReason(this.getReason());
		model.setDescription(this.getDescription());
		model.setStatus(this.getStatus());
		model.setCreatedDate(this.getCreatedDate());
		return model;
	}
}
