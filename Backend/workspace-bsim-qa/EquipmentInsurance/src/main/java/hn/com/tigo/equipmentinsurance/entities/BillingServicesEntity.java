package hn.com.tigo.equipmentinsurance.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.equipmentinsurance.models.BillingServicesModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "MEA_BILLING_SERVICES")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingServicesEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "SERVICE_CODE")
	private Long serviceCode;

	@Column(name = "SERVICE_NAME", length = 150)
	private String serviceName;

	@Column(name = "UNIT_PRICE")
	private Double totalQuantity;

	@Column(name = "CREATION_USER", length = 50)
	private String creationUser;

	@Column(name = "CREATION_DATE")
	private LocalDateTime creationDate;

	@Column(name = "MODIFICATION_USER", length = 50)
	private String modificationUser;

	@Column(name = "MODIFICATION_DATE")
	private LocalDateTime modificationDate;

	@Column(name = "STATUS")
	private Long status;

	public BillingServicesModel entityToModel() {
		BillingServicesModel model = new BillingServicesModel();
		model.setId(this.getId());
		model.setServiceCode(this.getServiceCode());
		model.setServiceName(this.getServiceName());
		model.setTotalQuantity(this.getTotalQuantity());
		model.setCreationUser(this.getCreationUser());
		model.setCreationDate(this.getCreationDate());
		model.setModificationUser(this.getModificationUser());
		model.setModificationDate(this.getModificationDate());
		model.setStatus(this.getStatus());

		return model;
	}
}
