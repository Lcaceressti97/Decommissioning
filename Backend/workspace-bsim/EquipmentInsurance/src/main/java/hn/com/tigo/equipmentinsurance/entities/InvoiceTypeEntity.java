package hn.com.tigo.equipmentinsurance.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.equipmentinsurance.models.InvoiceTypeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "MEA_INVOICE_TYPE")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceTypeEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "TYPE", length = 100)
	private String type;

	@Column(name = "DESCRIPTION", length = 150)
	private String description;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "CREATED", nullable = false)
	private LocalDateTime created;

	public InvoiceTypeModel entityToModel() {
		InvoiceTypeModel model = new InvoiceTypeModel();

		model.setId(this.getId());
		model.setType(this.getType());
		model.setDescription(this.getDescription());
		model.setStatus(this.getStatus());
		model.setCreated(this.getCreated());
		return model;
	}

}
