package hn.com.tigo.simcardinquiry.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import hn.com.tigo.simcardinquiry.models.SimcardSuppliersModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "SIMCARD_SUPPLIERS")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimcardSuppliersEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "SUPPLIER_NO")
	private Long supplierNo;

	@Column(name = "SUPPLIER_NAME")
	private String supplierName;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "POSTAL_MAIL")
	private String postalMail;

	@Column(name = "EMAIL_SUPPLIER")
	private String emailSupplier;

	@Column(name = "EMAIL_SEND")
	private String email;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "BASE_FILE")
	private String baseFile;

	@Column(name = "SUBJECT")
	private String subject;

	@Column(name = "TEXT_EMAIL")
	private String textEmail;

	@Column(name = "INITIAL_ICCD")
	private String initialIccd;

	@Column(name = "KEY")
	private String key;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "CREATED_DATE")
	private LocalDateTime createdDate;

	public SimcardSuppliersModel entityToModel() {
		SimcardSuppliersModel model = new SimcardSuppliersModel();
		model.setId(this.getId());
		model.setSupplierNo(this.getSupplierNo());
		model.setSupplierName(this.getSupplierName());
		model.setAddress(this.getAddress());
		model.setPhone(this.getPhone());
		model.setPostalMail(this.getPostalMail());
		model.setEmailSupplier(this.getEmailSupplier());
		model.setEmail(this.getEmail());
		model.setSubject(this.getSubject());
		model.setTextEmail(this.getTextEmail());
		model.setInitialIccd(this.getInitialIccd());
		model.setKey(this.getKey());
		model.setStatus(this.getStatus());
		model.setCreatedDate(this.getCreatedDate());

		return model;
	}
}
