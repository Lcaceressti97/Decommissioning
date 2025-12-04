package hn.com.tigo.comodatos.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.comodatos.models.ControlCancellationModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "CMD_CONTROL_CANCELLATION")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ControlCancellationEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID", nullable = false, updatable = false)
	private Long id;
	
	@Column(name = "ID_REFERENCE")
	private Long idReference;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "CANCELLATION_USER", length = 50)
	private String cancellationUser;
	
	@Column(name = "CREATED")
	private LocalDateTime created;
	
	/**
	 * Método que mapea la entidad con el dto "Modelo"
	 * 
	 * @return
	 */
	public ControlCancellationModel entityToModel() {
		ControlCancellationModel model = new ControlCancellationModel();
		model.setId(this.getId());
		model.setIdReference(this.getIdReference());
		model.setDescription(this.getDescription());
		model.setCancellationUser(this.getCancellationUser());
		model.setCreated(this.getCreated());
		return model;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdReference() {
		return idReference;
	}

	public void setIdReference(Long idReference) {
		this.idReference = idReference;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCancellationUser() {
		return cancellationUser;
	}

	public void setCancellationUser(String cancellationUser) {
		this.cancellationUser = cancellationUser;
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
