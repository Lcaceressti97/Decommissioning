package hn.com.tigo.equipmentaccessoriesbilling.entities.bsim;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.equipmentaccessoriesbilling.models.bsim.InvModelsOrArticlesModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "INV_MODELS_OR_ARTICLES", schema = "STCINVENTORY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvModelsOrArticlesEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false, updatable = false)
	private Long id;

	@Column(name = "CODE", length = 10)
	private String code;

	@Column(name = "EQUIPMENT_LINE_ID")
	private Long equipmentLineId;

	@Column(name = "EQUIPMENT_GROUP_ID")
	private Long equipmentGroupId;

	@Column(name = "MODEL", length = 30)
	private String model;

	@Column(name = "DESCRIPTION", length = 80)
	private String description;

	@Column(name = "VERSION")
	private Long version;

	@Column(name = "ACC_ACCOUNT_ID")
	private Long accAccountId;

	@Column(name = "BRAND_ID")
	private Long brandId;

	public InvModelsOrArticlesModel entityToModel() {
		InvModelsOrArticlesModel model = new InvModelsOrArticlesModel();

		model.setId(this.getId());
		model.setCode(this.getCode());
		model.setEquipmentLineId(this.getEquipmentLineId());
		model.setEquipmentGroupId(this.getEquipmentGroupId());
		model.setModel(this.getModel());
		model.setDescription(this.getDescription());
		model.setVersion(this.getVersion());
		model.setAccAccountId(this.getAccAccountId());
		model.setBrandId(this.getBrandId());

		return model;
	}
}
