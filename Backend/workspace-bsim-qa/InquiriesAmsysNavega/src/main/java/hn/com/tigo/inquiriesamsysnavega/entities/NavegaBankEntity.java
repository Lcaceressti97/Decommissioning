package hn.com.tigo.inquiriesamsysnavega.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.inquiriesamsysnavega.models.NavegaBankModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "CN_BANKS")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NavegaBankEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "CODE", length = 100)
	private String code;

	@Column(name = "NAME_COMPANY", length = 100)
	private String nameCompany;

	@Column(name = "BANK_NAME", length = 150)
	private String bankName;

	@Column(name = "NAME_TRANSACTION", length = 100)
	private String nameTransaction;

	@Column(name = "BANK_ACCOUNT_NAME", length = 100)
	private String bankAccountName;

	@Column(name = "BANK_ACCOUNT_NUM", length = 100)
	private String bankAccountNum;

	@Column(name = "CURRENCY_CODE", length = 50)
	private String currencyCode;

	@Column(name = "IDENTIFYING_LETTER", length = 5)
	private String identifyingLetter;

	@Column(name = "BANK_ACCOUNT_EBS", length = 100)
	private String bankAccountEbs;

	@Column(name = "BANK_CODE_AMSYS", length = 100)
	private String bankCodeAmsys;

	@Column(name = "COMPANY", length = 50)
	private String company;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "CREATED_DATE")
	private LocalDateTime createdDate;

	public NavegaBankModel entityToModel() {
		NavegaBankModel model = new NavegaBankModel();
		model.setId(this.getId());
		model.setCode(this.getCode());
		model.setNameCompany(this.getNameCompany());
		model.setBankName(this.getBankName());
		model.setNameTransaction(this.getNameTransaction());
		model.setBankAccountName(this.getBankAccountName());
		model.setBankAccountNum(this.getBankAccountNum());
		model.setCurrencyCode(this.getCurrencyCode());
		model.setIdentifyingLetter(this.getIdentifyingLetter());
		model.setBankAccountEbs(this.getBankAccountEbs());
		model.setBankCodeAmsys(this.getBankCodeAmsys());
		model.setCompany(this.getCompany());
		model.setStatus(this.getStatus());
		model.setCreatedDate(this.getCreatedDate());
		return model;
	}
}
