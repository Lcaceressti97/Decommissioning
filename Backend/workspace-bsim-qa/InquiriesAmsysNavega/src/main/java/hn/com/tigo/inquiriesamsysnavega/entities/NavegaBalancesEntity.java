package hn.com.tigo.inquiriesamsysnavega.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.inquiriesamsysnavega.models.NavegaBalancesModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "CN_NAVEGA_BALANCES")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NavegaBalancesEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "CUSTOMER_NAME")
	private String customerName;

	@Column(name = "CUSTOMER_NO")
	private String customerNo;

	@Column(name = "CUSTOMER_CODE")
	private String customerCode;

	@Column(name = "PRODUCT")
	private String product;

	@Column(name = "EBS_ACCOUNT")
	private String ebsAccount;

	@Column(name = "ID_ORGANIZATION")
	private Long idOrganization;

	@Column(name = "ORGANIZATION_NAME")
	private String organizationName;

	@Column(name = "CURRENCY")
	private String currency;

	@Column(name = "BALANCE")
	private Double balance;

	@Column(name = "CLOSING_DATE")
	private Date closingDate;

	public NavegaBalancesModel entityToModel() {
		NavegaBalancesModel model = new NavegaBalancesModel();
		model.setId(this.getId());
		model.setCustomerName(this.getCustomerName());
		model.setCustomerNo(this.getCustomerNo());
		model.setCustomerCode(this.getCustomerCode());
		model.setProduct(this.getProduct());
		model.setEbsAccount(this.getEbsAccount());
		model.setIdOrganization(this.getIdOrganization());
		model.setOrganizationName(this.getOrganizationName());
		model.setCurrency(this.getCurrency());
		model.setBalance(this.getBalance());
		model.setClosingDate(this.getClosingDate());
		return model;
	}

}
