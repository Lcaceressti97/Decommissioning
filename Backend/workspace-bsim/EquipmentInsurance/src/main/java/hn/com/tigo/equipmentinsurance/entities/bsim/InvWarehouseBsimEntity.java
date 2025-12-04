package hn.com.tigo.equipmentinsurance.entities.bsim;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.equipmentinsurance.models.bsim.InvWarehouseByLocationModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "INV_WAREHOUSE_BY_LOCATION", schema = "STCINVENTORY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvWarehouseBsimEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false, updatable = false)
	private Long id;

	@Column(name = "CODE", length = 6)
	private String code;

	@Column(name = "NAME", length = 80)
	private String name;

	@Column(name = "ADDRESS", length = 320)
	private String address;

	private String phone;

	private String zone;

	private String businessUnit;

	@Column(name = "STATUS", length = 1)
	private String status;

	public InvWarehouseByLocationModel entityToModel() {
		InvWarehouseByLocationModel model = new InvWarehouseByLocationModel();

		model.setId(this.getId());
		model.setCode(this.getCode());
		model.setName(this.getName());
		model.setAddress(this.getAddress());
		model.setPhone(this.getPhone());
		model.setZone(this.getZone());
		model.setBusinessUnit(this.getBusinessUnit());
		model.setStatus(this.getStatus());

		return model;
	}
}
