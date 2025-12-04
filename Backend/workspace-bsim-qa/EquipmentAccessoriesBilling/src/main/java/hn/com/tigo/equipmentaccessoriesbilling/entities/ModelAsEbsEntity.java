package hn.com.tigo.equipmentaccessoriesbilling.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.equipmentaccessoriesbilling.models.ModelAsEbsModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "MEA_MODELS_AS_EBS")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelAsEbsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "CODMOD", length = 50)
	private String codMod;

	@Column(name = "CODEBS", length = 50)
	private String codEbs;

	@Column(name = "SUBBOD", length = 50)
	private String subBod;

	@Column(name = "NEWMOD", length = 50)
	private String newMod;

	@Column(name = "NAME", length = 50)
	private String name;

	public ModelAsEbsModel entityToModel() {
		ModelAsEbsModel model = new ModelAsEbsModel();
		model.setId(this.getId());
		model.setCodMod(this.getCodMod().trim());
		model.setCodEbs(this.getCodEbs().trim());
		model.setSubBod(this.getSubBod().trim());
		model.setNewMod(this.getNewMod().trim());
		model.setName(this.getName().trim());

		return model;
	}
}
