package hn.com.tigo.equipmentaccessoriesbilling.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.equipmentaccessoriesbilling.models.TypeUserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "MEA_TYPE_USER")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeUserEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "TYPE_USER", length = 50)
	private String typeUser;

	@Column(name = "DESCRIPTION", length = 100)
	private String description;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "CREATED", nullable = false)
	private LocalDateTime created;

	public TypeUserModel entityToModel() {
		TypeUserModel model = new TypeUserModel();
		model.setId(this.getId());
		model.setTypeUser(this.getTypeUser());
		model.setDescription(this.getDescription());
		model.setStatus(this.getStatus());
		model.setCreated(this.getCreated());
		return model;
	}

}
