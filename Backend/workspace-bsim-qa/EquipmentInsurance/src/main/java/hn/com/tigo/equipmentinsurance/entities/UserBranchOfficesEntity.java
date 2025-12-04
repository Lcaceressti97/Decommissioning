package hn.com.tigo.equipmentinsurance.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.equipmentinsurance.models.UserBranchOfficesModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "MEA_USER_BRANCH_OFFICES")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBranchOfficesEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "ID_USER")
	private Long idUser;

	@Column(name = "ID_BRANCH_OFFICES")
	private Long idBranchOffices;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "CREATED", nullable = false)
	private LocalDateTime created;

	public UserBranchOfficesModel entityToModel() {
		UserBranchOfficesModel model = new UserBranchOfficesModel();
		model.setId(this.getId());
		model.setIdUser(this.getIdUser());
		model.setIdBranchOffices(this.getIdBranchOffices());
		model.setStatus(this.getStatus());
		model.setCreated(this.getCreated());
		return model;
	}
}
