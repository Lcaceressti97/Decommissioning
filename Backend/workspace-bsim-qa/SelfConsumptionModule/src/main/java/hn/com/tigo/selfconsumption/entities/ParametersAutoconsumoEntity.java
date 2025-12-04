package hn.com.tigo.selfconsumption.entities;

import java.time.LocalDateTime;

import hn.com.tigo.selfconsumption.models.ParametersAutoconsumoModel;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "PARAMETERS_AUTOCONSUMO")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParametersAutoconsumoEntity {

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;
	
	@Column(name = "ID_APPLICATION")
	private Long idApplication;
	
	@Column(name = "NAME", length = 100)
	private String name;
	
	@Column(name = "VALUE", length = 2000)
	private String value;
	
	@Column(name = "DESCRIPTION", length = 800)
	private String description;
	
	@Column(name = "STATUS")
	private Long status;

	@Column(name = "CREATED_DATE", nullable = false)
	private LocalDateTime createdDate;
	
	public ParametersAutoconsumoModel entityToModel() {
		ParametersAutoconsumoModel model = new ParametersAutoconsumoModel();
		model.setId(this.getId());
		model.setIdApplication(this.getIdApplication());
		model.setName(this.getName());
		model.setValue(this.getValue());
		model.setDescription(this.getDescription());
		model.setStatus(this.getStatus());
		model.setCreatedDate(this.getCreatedDate());
		return model;
	}
}
