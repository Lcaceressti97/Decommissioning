package hn.com.tigo.equipmentaccessoriesbilling.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.equipmentaccessoriesbilling.models.CancelReasonModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "MEA_CANCEL_REASON")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelReasonEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "REASON_CODE")
	private Long reasonCode;

	@Column(name = "REASON_DESC", length = 250)
	private String reasonDesc;

	@Column(name = "INVENTORY_TYPE", length = 50)
	private String inventoryType;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "CREATED", nullable = false)
	private LocalDateTime created;

	public CancelReasonModel entityToModel() {
		CancelReasonModel model = new CancelReasonModel();

		model.setId(this.getId());
		model.setReasonCode(this.getReasonCode());
		model.setReasonDesc(this.getReasonDesc());
		model.setInventoryType(this.getInventoryType());
		model.setStatus(this.getStatus());
		model.setCreated(this.getCreated());
		return model;
	}
}
