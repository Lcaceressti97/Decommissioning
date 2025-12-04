package hn.com.tigo.tool.annotations.decommissioning.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.tool.annotations.decommissioning.models.ParametersModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Laurent G. Cáceres
 */
@Table(name = "CONFIG_PARAMETERS")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParametersEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_APPLICATION", insertable = false, updatable = false)
	/** Attribute that determine id. */
	private long idApplication;

	@Column(name = "NAME", length = 100)
	/** Attribute that determine name. */
	private String name;

	@Column(name = "VALUE", length = 2000)
	/** Attribute that determine value. */
	private String value;

	@Column(name = "DESCRIPTION", length = 800)
	/** Attribute that determine description. */
	private String description;

	@Column(name = "CREATED_DATE")
	/** Attribute that determine createdDate. */
	private LocalDateTime createdDate;

	public ParametersModel entityToModel() {
		ParametersModel model = new ParametersModel();
		model.setIdApplication(this.getIdApplication());
		model.setName(this.getName());
		model.setValue(this.getValue());
		model.setDescription(this.getDescription());
		model.setCreatedDate(this.getCreatedDate());
		return model;
	}

}
