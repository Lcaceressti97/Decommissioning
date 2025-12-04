package hn.com.tigo.equipmentblacklist.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

import hn.com.tigo.equipmentblacklist.models.ReasonsModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "BL_REASONS")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReasonsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "REASONS", length = 50)
	private String reasons;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "CREATED_DATE")
	private LocalDateTime createdDate;

	public ReasonsModel entityToModel() {
		ReasonsModel model = new ReasonsModel();
		model.setId(this.getId());
		model.setReasons(this.getReasons());
		model.setStatus(this.getStatus());
		model.setCreatedDate(this.getCreatedDate());
		return model;
	}
}
