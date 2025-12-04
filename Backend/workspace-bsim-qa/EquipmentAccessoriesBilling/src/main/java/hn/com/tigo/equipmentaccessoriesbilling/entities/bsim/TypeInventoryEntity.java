package hn.com.tigo.equipmentaccessoriesbilling.entities.bsim;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.equipmentaccessoriesbilling.models.bsim.TypeInventoryModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "INV_WAREHOUSE_BY_LOCATION", schema = "STCINVENTORY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeInventoryEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false, updatable = false)
	private Long id;

	@Column(name = "VERSION")
	private BigDecimal version;

	@Column(name = "TYPE", length = 1)
	private String type;

	@Column(name = "LONG_DESCRIPTION", length = 160)
	private String longDescription;

	@Column(name = "SHORT_DESCRIPTION", length = 80)
	private String shortDescription;

	@Column(name = "EXTERNAL_CODE", length = 30)
	private String externalCode;

	public TypeInventoryModel entityToModel() {
		TypeInventoryModel model = new TypeInventoryModel();
		model.setId(this.getId());
		model.setVersion(this.getVersion());
		model.setType(this.getType());
		model.setLongDescription(this.getLongDescription());
		model.setShortDescription(this.getShortDescription());
		model.setExternalCode(this.getExternalCode());
		return model;
	}
}
