package hn.com.tigo.simcardinquiry.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.simcardinquiry.models.SimcardVersionModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "SIMCARD_VERSION")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimcardVersionEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "ID_MODEL")
	private Long idModel;

	@Column(name = "VERSION")
	private String version;

	@Column(name = "VERSION_SIZE")
	private String versionSize;

	@Column(name = "VERSION_DESCRIPTION")
	private String versionDescription;

	@Column(name = "CAPACITY", length = 150)
	private String capacity;

	@Column(name = "VERSION_DETAIL", length = 150)
	private String versionDetail;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "CREATED_DATE")
	private LocalDateTime createdDate;

	public SimcardVersionModel entityToModel() {
		SimcardVersionModel model = new SimcardVersionModel();
		model.setId(this.getId());
		model.setIdModel(this.getIdModel());
		model.setVersion(this.getVersion());
		model.setVersionSize(this.getVersionSize());
		model.setVersionDescription(this.getVersionDescription());
		model.setCapacity(this.getCapacity());
		model.setVersionDetail(this.getVersionDetail());
		model.setStatus(this.getStatus());
		model.setCreatedDate(this.getCreatedDate());
		return model;
	}
}
