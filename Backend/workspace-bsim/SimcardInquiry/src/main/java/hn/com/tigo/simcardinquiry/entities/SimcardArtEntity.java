package hn.com.tigo.simcardinquiry.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.simcardinquiry.models.SimcardArtModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "SIMCARD_ART")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimcardArtEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "ART_NUMBER")
	private String artNumber;

	@Column(name = "ART_DESCRIPTION", length = 150)
	private String artDescription;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "CREATED_DATE")
	private LocalDateTime createdDate;

	public SimcardArtModel entityToModel() {
		SimcardArtModel model = new SimcardArtModel();
		model.setId(this.getId());
		model.setArtNumber(this.getArtNumber());
		model.setArtDescription(this.getArtDescription());
		model.setStatus(this.getStatus());
		model.setCreatedDate(this.getCreatedDate());
		return model;
	}
}
