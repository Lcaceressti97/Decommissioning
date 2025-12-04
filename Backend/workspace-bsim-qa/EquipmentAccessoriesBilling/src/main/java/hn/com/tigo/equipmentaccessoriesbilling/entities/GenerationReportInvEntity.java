package hn.com.tigo.equipmentaccessoriesbilling.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import hn.com.tigo.equipmentaccessoriesbilling.models.GenerationReportInvModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "MEA_GENERATION_REPORT_INV")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerationReportInvEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "FILE_NAME", length = 100)
	private String fileName;

	@Column(name = "DESCRIPTION", length = 100)
	private String description;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "REPORT_INV")
	private String reportInv;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "CREATED")
	private LocalDateTime created;

	public GenerationReportInvModel entityToModel() {
		GenerationReportInvModel model = new GenerationReportInvModel();
		model.setId(this.getId());
		model.setFileName(this.getFileName());
		model.setDescription(this.getDescription());
		model.setReportInv(this.getReportInv());
		model.setStatus(this.status);
		model.setCreated(this.created);
		return model;
	}

}
