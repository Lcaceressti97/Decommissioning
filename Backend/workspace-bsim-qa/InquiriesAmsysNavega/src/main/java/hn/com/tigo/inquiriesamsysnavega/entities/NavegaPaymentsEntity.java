package hn.com.tigo.inquiriesamsysnavega.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.inquiriesamsysnavega.models.NavegaPaymentsModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "CN_NAVEGA_PAYMENTS")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NavegaPaymentsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "NAVEGA_CODE")
	private String navegaCode;

	@Column(name = "PRODUCT_CODE")
	private Integer productCode;

	@Column(name = "CURRENCY")
	private String currency;

	@Column(name = "PAYMENT_AMOUNT")
	private Double paymentAmount;

	@Column(name = "BANK")
	private String bank;

	@Column(name = "BANK_ACCT")
	private String bankAcct;

	@Column(name = "BANK_AUTHORIZATION")
	private Long bankAuthorization;

	@Column(name = "EXCHANGE_RATE")
	private Double exchangeRate;

	@Column(name = "PAYMENT_DATE")
	private LocalDateTime paymentDate;

	@Column(name = "ID_ORGANIZATION")
	private Integer idOrganization;

	@Column(name = "EBS_ACCOUNT")
	private String ebsAccount;

	@Column(name = "TRANSACTION_STS")
	private String transactionSts;

	@Column(name = "OU_NAME")
	private String ouName;

	@Column(name = "SYNCHROM")
	private String synchron;

	@Column(name = "REC_METHOD")
	private String recMethod;

	@Column(name = "RECEIP_DATE")
	private LocalDateTime receiptDate;

	@Column(name = "GL_DATE")
	private LocalDateTime glDate;

	@Column(name = "REC_NUMBER")
	private String recNumber;

	@Column(name = "REC_AMOUNT")
	private Integer recAmount;

	@Column(name = "CUST_ACCT_NUM")
	private String custAcctNum;

	@Column(name = "COMMENTS")
	private String comments;

	@Column(name = "ATTRIBUTE_CAT")
	private String attributeCat;

	@Column(name = "ATTRIBUTE_1")
	private String attribute1;

	@Column(name = "ATTRIBUTE_2")
	private String attribute2;

	@Column(name = "ATTRIBUTE_3")
	private String attribute3;

	@Column(name = "RECEIPT_NUMBER")
	private BigDecimal receiptNumber;

	public NavegaPaymentsModel entityToModel() {
		NavegaPaymentsModel model = new NavegaPaymentsModel();
		model.setId(this.getId());
		model.setNavegaCode(this.getNavegaCode());
		model.setProductCode(this.getProductCode());
		model.setCurrency(this.getCurrency());
		model.setPaymentAmount(this.getPaymentAmount());
		model.setBank(this.getBank());
		model.setBankAcct(this.getBankAcct());
		model.setBankAuthorization(this.getBankAuthorization());
		model.setExchangeRate(this.getExchangeRate());
		model.setPaymentDate(this.getPaymentDate());
		model.setIdOrganization(this.getIdOrganization());
		model.setEbsAccount(this.getEbsAccount());
		model.setTransactionSts(this.getTransactionSts());
		model.setOuName(this.getOuName());
		model.setSynchron(this.getSynchron());
		model.setRecMethod(this.getRecMethod());
		model.setReceiptDate(this.getReceiptDate());
		model.setGlDate(this.getGlDate());
		model.setRecNumber(this.getRecNumber());
		model.setRecAmount(this.getRecAmount());
		model.setCustAcctNum(this.getCustAcctNum());
		model.setComments(this.getComments());
		model.setAttributeCat(this.getAttributeCat());
		model.setAttribute1(this.getAttribute1());
		model.setAttribute2(this.getAttribute2());
		model.setAttribute3(this.getAttribute3());
		model.setReceiptNumber(this.getReceiptNumber());
		return model;
	}
}
