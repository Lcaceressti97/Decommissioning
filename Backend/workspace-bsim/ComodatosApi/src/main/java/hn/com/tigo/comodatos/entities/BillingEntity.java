package hn.com.tigo.comodatos.entities;

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

import hn.com.tigo.comodatos.models.BillingModel;
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

	@Column(name = "CHANNEL")
	private Long channel;

	@Column(name = "EXEMPT_AMOUNT")
	private Double exemptAmount;

	@Column(name = "CUSTOMER_ADDRESS", length = 150)
	private String customerAddress;

	@Column(name = "CUSTOMER_RTN_ID", length = 50)
	private String customerRtnId;

	@Column(name = "SELLER_NAME", length = 100)
	private String sellerName;

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
		model.setChannel(this.getChannel());
		model.setExemptAmount(this.getExemptAmount());
		model.setCustomerAddress(this.getCustomerAddress());
		model.setCustomerRtnId(this.getCustomerRtnId());
		model.setSellerName(this.getSellerName());
		model.setInvoiceDetails(this.getInvoiceDetails());
		return model;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public Long getIdReference() {
		return idReference;
	}

	public void setIdReference(Long idReference) {
		this.idReference = idReference;
	}

	public String getAcctCode() {
		return acctCode;
	}

	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}

	public String getPrimaryIdentity() {
		return primaryIdentity;
	}

	public void setPrimaryIdentity(String primaryIdentity) {
		this.primaryIdentity = primaryIdentity;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getDiplomaticCardNo() {
		return diplomaticCardNo;
	}

	public void setDiplomaticCardNo(String diplomaticCardNo) {
		this.diplomaticCardNo = diplomaticCardNo;
	}

	public String getCorrelativeOrdenExemptNo() {
		return correlativeOrdenExemptNo;
	}

	public void setCorrelativeOrdenExemptNo(String correlativeOrdenExemptNo) {
		this.correlativeOrdenExemptNo = correlativeOrdenExemptNo;
	}

	public String getCorrelativeCertificateExoNo() {
		return correlativeCertificateExoNo;
	}

	public void setCorrelativeCertificateExoNo(String correlativeCertificateExoNo) {
		this.correlativeCertificateExoNo = correlativeCertificateExoNo;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getCai() {
		return cai;
	}

	public void setCai(String cai) {
		this.cai = cai;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCashierName() {
		return cashierName;
	}

	public void setCashierName(String cashierName) {
		this.cashierName = cashierName;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public Long getIdBranchOffices() {
		return idBranchOffices;
	}

	public void setIdBranchOffices(Long idBranchOffices) {
		this.idBranchOffices = idBranchOffices;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

	public String getInitialRank() {
		return initialRank;
	}

	public void setInitialRank(String initialRank) {
		this.initialRank = initialRank;
	}

	public String getFinalRank() {
		return finalRank;
	}

	public void setFinalRank(String finalRank) {
		this.finalRank = finalRank;
	}

	public Long getIdCompany() {
		return idCompany;
	}

	public void setIdCompany(Long idCompany) {
		this.idCompany = idCompany;
	}

	public Long getIdSystem() {
		return idSystem;
	}

	public void setIdSystem(Long idSystem) {
		this.idSystem = idSystem;
	}

	public String getNumberDei() {
		return numberDei;
	}

	public void setNumberDei(String numberDei) {
		this.numberDei = numberDei;
	}

	public String getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(String deadLine) {
		this.deadLine = deadLine;
	}

	public Long getIdVoucher() {
		return idVoucher;
	}

	public void setIdVoucher(Long idVoucher) {
		this.idVoucher = idVoucher;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public Double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(Double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public Double getTaxPercentage() {
		return taxPercentage;
	}

	public void setTaxPercentage(Double taxPercentage) {
		this.taxPercentage = taxPercentage;
	}

	public Double getAmountTax() {
		return amountTax;
	}

	public void setAmountTax(Double amountTax) {
		this.amountTax = amountTax;
	}

	public Double getAmountExo() {
		return amountExo;
	}

	public void setAmountExo(Double amountExo) {
		this.amountExo = amountExo;
	}

	public Double getAmountTotal() {
		return amountTotal;
	}

	public void setAmountTotal(Double amountTotal) {
		this.amountTotal = amountTotal;
	}

	public String getAuthorizingUser() {
		return authorizingUser;
	}

	public void setAuthorizingUser(String authorizingUser) {
		this.authorizingUser = authorizingUser;
	}

	public LocalDateTime getAuthorizationDate() {
		return authorizationDate;
	}

	public void setAuthorizationDate(LocalDateTime authorizationDate) {
		this.authorizationDate = authorizationDate;
	}

	public String getUserIssued() {
		return userIssued;
	}

	public void setUserIssued(String userIssued) {
		this.userIssued = userIssued;
	}

	public LocalDateTime getDateOfIssue() {
		return dateOfIssue;
	}

	public void setDateOfIssue(LocalDateTime dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public Long getExonerationStatus() {
		return exonerationStatus;
	}

	public void setExonerationStatus(Long exonerationStatus) {
		this.exonerationStatus = exonerationStatus;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public String getInventoryType() {
		return inventoryType;
	}

	public void setInventoryType(String inventoryType) {
		this.inventoryType = inventoryType;
	}

	public String getSubWarehouse() {
		return subWarehouse;
	}

	public void setSubWarehouse(String subWarehouse) {
		this.subWarehouse = subWarehouse;
	}

	public Long getChannel() {
		return channel;
	}

	public void setChannel(Long channel) {
		this.channel = channel;
	}

	public Double getExemptAmount() {
		return exemptAmount;
	}

	public void setExemptAmount(Double exemptAmount) {
		this.exemptAmount = exemptAmount;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerRtnId() {
		return customerRtnId;
	}

	public void setCustomerRtnId(String customerRtnId) {
		this.customerRtnId = customerRtnId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public List<InvoiceDetailEntity> getInvoiceDetails() {
		return invoiceDetails;
	}

	public void setInvoiceDetails(List<InvoiceDetailEntity> invoiceDetails) {
		this.invoiceDetails = invoiceDetails;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
