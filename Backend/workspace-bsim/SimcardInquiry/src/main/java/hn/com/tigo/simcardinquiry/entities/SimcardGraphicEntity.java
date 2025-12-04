package hn.com.tigo.simcardinquiry.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.simcardinquiry.models.SimcardGraphicModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "SIMCARD_GRAPHIC")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimcardGraphicEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "GRAPHIC_REF")
	private String graphicRef;

	@Column(name = "GRAPHIC_DESCRIPTION", length = 150)
	private String graphicDescription;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "CREATED_DATE")
	private LocalDateTime createdDate;

	public SimcardGraphicModel entityToModel() {
		SimcardGraphicModel model = new SimcardGraphicModel();
		model.setId(this.getId());
		model.setGraphicRef(this.getGraphicRef());
		model.setGraphicDescription(this.getGraphicDescription());
		model.setStatus(this.getStatus());
		model.setCreatedDate(this.getCreatedDate());
		return model;
	}
}
