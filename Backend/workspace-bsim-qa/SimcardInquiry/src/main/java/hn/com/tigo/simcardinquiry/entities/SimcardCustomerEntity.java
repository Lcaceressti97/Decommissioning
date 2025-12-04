package hn.com.tigo.simcardinquiry.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.simcardinquiry.models.SimcardCustomerModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "SIMCARD_CUSTOMERS")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimcardCustomerEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "CODE")
	private Long code;

	@Column(name = "HLR")
	private String hlr;

	@Column(name = "CLIENT_NAME")
	private String clientName;

	@Column(name = "DESCRIPTION_HRL")
	private String descriptionHrl;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "CREATED_DATE")
	private LocalDateTime createdDate;

	public SimcardCustomerModel entityToModel() {
		SimcardCustomerModel model = new SimcardCustomerModel();
		model.setId(this.getId());
		model.setCode(this.getCode());
		model.setHlr(this.getHlr());
		model.setClientName(this.getClientName());
		model.setDescriptionHrl(this.getDescriptionHrl());
		model.setStatus(this.getStatus());
		model.setCreatedDate(this.getCreatedDate());
		return model;
	}
}
