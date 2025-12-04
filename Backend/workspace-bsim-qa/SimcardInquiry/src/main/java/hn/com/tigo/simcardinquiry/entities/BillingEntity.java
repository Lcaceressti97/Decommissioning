package hn.com.tigo.simcardinquiry.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import hn.com.tigo.simcardinquiry.models.BillingModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "MEA_BILLING")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Entity
//@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BillingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_MEA_BILLING")
	@SequenceGenerator(name = "SQ_MEA_BILLING", sequenceName = "SQ_MEA_BILLING", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@Column(name = "INVOICE_NO", length = 50)
	private String invoiceNo;

	@Column(name = "INVOICE_TYPE", length = 50)
	private String invoiceType;

	@Column(name = "ID_REFERENCE")
	private Long idReference;

	@Column(name = "ACCTCODE", length = 30)
	private String acctCode;

	@Column(name = "PRIMARY_IDENTITY", length = 50)
	private String primaryIdentity;

	@Column(name = "DOCUMENT_NO", length = 100)
	private String documentNo;

	@Column(name = "DIPLOMATIC_CARD_NO", length = 100)
	private String diplomaticCardNo;

	@Column(name = "CORRELATIVE_ORDEN_EXEMPT_NO", length = 100)
	private String correlativeOrdenExemptNo;

	@Column(name = "CORRELATIVE_CERTIFICATE_EXO_NO", length = 100)
	private String correlativeCertificateExoNo;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "XML", length = 100)
	private String xml;

	@Column(name = "CAI", length = 100)
	private String cai;

	@Column(name = "CUSTOMER", length = 100)
	private String customer;

	@Column(name = "CUSTOMER_ID", length = 50)
	private String customerId;

	@Column(name = "CASHIER_NAME", length = 100)
	private String cashierName;

	@Column(name = "SELLER", length = 100)
	private String seller;

	@Column(name = "SELLER_CODE", length = 50)
	private String sellerCode;

	@Column(name = "ID_BRANCH_OFFICES")
	private Long idBranchOffices;

	@Column(name = "AGENCY", length = 100)
	private String agency;

	@Column(name = "WAREHOUSE", length = 100)
	private String warehouse;

	@Column(name = "DUE_DATE")
	private LocalDateTime dueDate;

	@Column(name = "INITIAL_RANK", length = 150)
	private String initialRank;

	@Column(name = "FINAL_RANK", length = 150)
	private String finalRank;

	@Column(name = "ID_COMPANY")
	private Long idCompany;

	@Column(name = "ID_SYSTEM")
	private Long idSystem;

	@Column(name = "NUMBER_DEI", length = 50)
	private String numberDei;

	@Column(name = "DEADLINE", length = 50)
	private String deadLine;

	@Column(name = "ID_VOUCHER")
	private Long idVoucher;

	@Column(name = "SUBTOTAL")
	private Double subtotal;

	@Column(name = "EXCHANGE_RATE")
	private Double exchangeRate;

	@Column(name = "DISCOUNT")
	private Double discount;

	@Column(name = "DISCOUNT_PERCENTAGE")
	private Double discountPercentage;

	@Column(name = "TAX")
	private Double taxPercentage;

	@Column(name = "AMOUNT_TAX")
	private Double amountTax;

	@Column(name = "AMOUNT_EXO")
	private Double amountExo;

	@Column(name = "AMOUNT_TOTAL")
	private Double amountTotal;

	@Column(name = "AUTHORIZING_USER", length = 50)
	private String authorizingUser;

	@Column(name = "AUTHORIZATION_DATE")
	private LocalDateTime authorizationDate;

	@Column(name = "USER_ISSUED", length = 50)
	private String userIssued;

	@Column(name = "DATE_OF_ISSUE")
	private LocalDateTime dateOfIssue;

	@Column(name = "EXONERATION_STATUS")
	private Long exonerationStatus;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "CREATED", nullable = false)
	private LocalDateTime created;

	@Column(name = "INVENTORY_TYPE", length = 50)
	private String inventoryType;

	@Column(name = "SUB_WAREHOUSE", length = 100)
	private String subWarehouse;

	@OneToMany(mappedBy = "billing", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<InvoiceDetailEntity> invoiceDetails;

	public BillingModel entityToModel() {
		BillingModel model = new BillingModel();

		model.setId(this.getId());
		model.setInvoiceNo(this.getInvoiceNo());
		model.setIdReference(this.getIdReference());
		model.setInvoiceType(this.getInvoiceType());
		model.setAcctCode(this.getAcctCode());
		model.setPrimaryIdentity(this.getPrimaryIdentity());
		model.setDocumentNo(this.getDocumentNo());
		model.setDiplomaticCardNo(this.getDiplomaticCardNo());
		model.setCorrelativeOrdenExemptNo(this.getCorrelativeOrdenExemptNo());
		model.setCorrelativeCertificateExoNo(this.getCorrelativeCertificateExoNo());
		model.setXml(this.getXml());
		model.setCai(this.getCai());
		model.setCustomer(this.getCustomer());
		model.setSeller(this.getSeller());
		model.setSellerCode(this.getSellerCode());
		model.setCashierName(this.getCashierName());
		model.setWarehouse(this.getWarehouse());
		model.setAgency(this.getAgency());
		model.setIdBranchOffices(this.getIdBranchOffices());
		model.setDueDate(this.getDueDate());
		model.setInitialRank(this.getInitialRank());
		model.setFinalRank(this.getFinalRank());
		model.setSubtotal(this.getSubtotal());
		model.setExchangeRate(this.getExchangeRate());
		model.setDiscount(this.getDiscount());
		model.setDiscountPercentage(this.getDiscountPercentage());
		model.setTaxPercentage(this.getTaxPercentage());
		model.setAmountTax(this.getAmountTax());
		model.setAmountExo(this.getAmountExo());
		model.setAmountTotal(this.getAmountTotal());
		model.setAuthorizingUser(this.getAuthorizingUser());
		model.setAuthorizationDate(this.getAuthorizationDate());
		model.setUserIssued(this.getUserIssued());
		model.setDateOfIssue(this.getDateOfIssue());
		model.setExonerationStatus(this.getExonerationStatus());
		model.setStatus(this.getStatus());
		model.setCreated(this.getCreated());
		model.setIdCompany(this.getIdCompany());
		model.setIdSystem(this.getIdSystem());
		model.setNumberDei(this.getNumberDei());
		model.setDeadLine(this.getDeadLine());
		model.setIdVoucher(this.getIdVoucher());
		model.setCustomerId(this.getCustomerId());
		model.setInventoryType(this.getInventoryType());
		model.setSubWarehouse(this.getSubWarehouse());
		model.setInvoiceDetails(this.getInvoiceDetails());
		return model;
	}
}
