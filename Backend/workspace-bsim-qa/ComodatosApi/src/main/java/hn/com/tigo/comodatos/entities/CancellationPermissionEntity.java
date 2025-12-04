package hn.com.tigo.comodatos.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.comodatos.models.CancellationPermissionModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "CMD_CANCELLATION_PERMISSION")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancellationPermissionEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID", nullable = false, updatable = false)
	private Long id;
	
	@Column(name = "USER_NAME", length = 50)
	private String userName;
	
	@Column(name = "PERMIT_STATUS", length = 1)
	private String permitStatus;
	
	@Column(name = "CREATED")
	private LocalDateTime created;
	
	/**
	 * Método que mapea la entidad con el dto "Modelo"
	 * 
	 * @return
	 */
	public CancellationPermissionModel entityToModel() {
		CancellationPermissionModel model = new CancellationPermissionModel();
		model.setId(this.getId());
		model.setUserName(this.getUserName());
		model.setPermitStatus(this.getPermitStatus());
		model.setCreated(this.getCreated());
		return model;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPermitStatus() {
		return permitStatus;
	}

	public void setPermitStatus(String permitStatus) {
		this.permitStatus = permitStatus;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
