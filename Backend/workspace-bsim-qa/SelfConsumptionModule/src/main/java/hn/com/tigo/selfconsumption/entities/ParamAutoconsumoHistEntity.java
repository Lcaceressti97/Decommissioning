package hn.com.tigo.selfconsumption.entities;

import java.time.LocalDateTime;

import hn.com.tigo.selfconsumption.models.ParamAutoconsumoHistModel;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "PARAMETERS_AUTOCONSUMO_HIST")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParamAutoconsumoHistEntity {

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "ID_PARAMETER")
	private Long idParameter;

	@Column(name = "NAME", length = 100)
	private String name;

	@Column(name = "VALUE", length = 2000)
	private String value;

	@Column(name = "DESCRIPTION", length = 800)
	private String description;

	@Column(name = "USER_NAME", length = 100)
	private String userName;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "CREATED_DATE", nullable = false)
	private LocalDateTime createDate;

	public ParamAutoconsumoHistModel entityToModel() {
		ParamAutoconsumoHistModel model = new ParamAutoconsumoHistModel();
		model.setId(this.getId());
		model.setIdParameter(this.getIdParameter());
		model.setName(this.getName());
		model.setValue(this.getValue());
		model.setDescription(this.getDescription());
		model.setUserName(this.getUserName());
		model.setStatus(this.getStatus());
		model.setCreateDate(this.getCreateDate());
		return model;
	}
}
