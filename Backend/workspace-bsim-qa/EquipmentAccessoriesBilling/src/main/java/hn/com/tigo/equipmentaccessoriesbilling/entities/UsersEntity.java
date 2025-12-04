package hn.com.tigo.equipmentaccessoriesbilling.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

import hn.com.tigo.equipmentaccessoriesbilling.models.UsersModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "MEA_USERS")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "NAME", length = 100)
	private String name;

	@Column(name = "USER_NAME", length = 100)
	private String userName;

	@Column(name = "EMAIL", length = 100)
	private String email;

	@Column(name = "TYPE_USER")
	private Long typeUser;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "CREATED", nullable = false)
	private LocalDateTime created;

	public UsersModel entityToModel() {
		UsersModel model = new UsersModel();
		model.setId(this.getId());
		model.setName(this.getName());
		model.setUserName(this.getUserName());
		model.setEmail(this.getEmail());
		model.setTypeUser(this.getTypeUser());
		model.setStatus(this.getStatus());
		model.setCreated(this.getCreated());
		return model;
	}
}
