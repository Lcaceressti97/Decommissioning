package hn.com.tigo.equipmentaccessoriesbilling.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.equipmentaccessoriesbilling.models.ApprovalsModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "MEA_APPROVALS")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "ID_APPROVAL")
	private Long idApproval;

	@Column(name = "APPROVED_USER", length = 100)
	private String approvedUser;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "CREATED")
	private LocalDateTime created;

	public ApprovalsModel entityToModel() {
		ApprovalsModel model = new ApprovalsModel();

		model.setId(this.getId());
		model.setIdApproval(this.getIdApproval());
		model.setApprovedUser(this.getApprovedUser());
		model.setStatus(this.getStatus());
		model.setCreated(this.getCreated());
		return model;
	}

}
