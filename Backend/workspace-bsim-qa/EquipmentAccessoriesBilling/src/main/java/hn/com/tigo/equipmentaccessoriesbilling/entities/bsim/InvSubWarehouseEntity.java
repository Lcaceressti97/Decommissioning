package hn.com.tigo.equipmentaccessoriesbilling.entities.bsim;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.equipmentaccessoriesbilling.models.bsim.InvSubWarehouseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "INV_SUB_WAREHOUSES", schema = "STCINVENTORY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvSubWarehouseEntity implements Serializable {

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

	public InvSubWarehouseModel entityToModel() {
		InvSubWarehouseModel model = new InvSubWarehouseModel();

		model.setId(this.getId());
		model.setVersion(this.getVersion());
		model.setCode(this.getCode());
		model.setName(this.getName());

		return model;
	}
}
