package hn.com.tigo.equipmentinsurance.entities.bsim;

import java.io.Serializable;
import java.math.BigDecimal;

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
public class InvWarehouseByLocationEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false, updatable = false)
	private Long id;

	@Column(name = "VERSION")
	private BigDecimal version;

	@Column(name = "CODE", length = 6)
	private String code;

	@Column(name = "NAME", length = 80)
	private String name;

	@Column(name = "ADDRESS", length = 320)
	private String address;
	
	private String phone;
	
	private String zone;
	
	private String businessUnit;

	@Column(name = "RESPONSIBLE", length = 320)
	private String responsible;

	@Column(name = "STATUS", length = 1)
	private String status;

	@Column(name = "WAREHOUSE_TYPE_ID")
	private String warehouseTypeId;

	@Column(name = "IMMEDIATE_BOSS", length = 40)
	private String immediateBoss;

	public InvWarehouseByLocationModel entityToModel() {
		InvWarehouseByLocationModel model = new InvWarehouseByLocationModel();

		model.setId(this.getId());
		model.setVersion(this.getVersion());
		model.setCode(this.getCode());
		model.setName(this.getName());
		model.setAddress(this.getAddress());
		model.setPhone(this.getPhone());
		model.setZone(this.getZone());
		model.setBusinessUnit(this.getBusinessUnit());
		model.setResponsible(this.getResponsible());
		model.setStatus(this.getStatus());
		model.setWarehouseTypeId(this.getWarehouseTypeId());
		model.setImmediateBoss(this.getImmediateBoss());

		return model;
	}
}
