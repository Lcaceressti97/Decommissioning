package hn.com.tigo.comodatos.models;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonInclude;

import hn.com.tigo.comodatos.entities.InvoiceDetailEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillingModel {

	private Long id;

	@NotNull(message = "INVOICE_NO cannot be null")
	@NotBlank(message = "INVOICE_NO cannot be blank")
	private String invoiceNo;

	@NotNull(message = "INVOICE_TYPE cannot be null")
	@NotBlank(message = "INVOICE_TYPE cannot be blank")
	@Pattern(regexp = "^[a-zA-Z]+[a-zA-Z0-9]*$|^FS[1-4]$|^FC[1-2]$|^XSA$|^SHP2$", message = "INVOICE_TYPE must start with letters and can contain letters and numbers, or one of the following codes: FS1, FS2, FS3, FS4, FC1, FC2, XSA, SHP2")
	private String invoiceType;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long idReference;

	@NotNull(message = "ACCTCODE cannot be null")
	@NotBlank(message = "ACCTCODE cannot be blank")
	@Pattern(regexp = "^[0-9]+$", message = "ACCTCODE must contain only numbers")
	private String acctCode;

	@NotNull(message = "PRIMARY_IDENTITY cannot be null")
	@NotBlank(message = "PRIMARY_IDENTITY cannot be blank")
	@Pattern(regexp = "^[0-9]+$", message = "PRIMARY_IDENTITY must contain only numbers")
	private String primaryIdentity;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String documentNo;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String diplomaticCardNo;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String correlativeOrdenExemptNo;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String correlativeCertificateExoNo;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String xml;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String cai;

	@NotNull(message = "CUSTOMER cannot be null")
	@NotBlank(message = "CUSTOMER cannot be blank")
	private String customer;

	private String customerId;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String cashierName;

	@NotNull(message = "SELLER cannot be null")
	@NotBlank(message = "SELLER cannot be blank")
	private String seller;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String sellerCode;

	private Long idBranchOffices;

	private String agency;

	// @JsonInclude(JsonInclude.Include.NON_NULL)
	private String warehouse;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime dueDate;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String initialRank;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String finalRank;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long idCompany;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long idSystem;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String numberDei;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String deadLine;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long idVoucher;

	@NotNull(message = "SUBTOTAL cannot be null")
	@Positive(message = "SUBTOTAL must be a positive number")
	private Double subtotal;

	private Double exchangeRate;

	private Double discount;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Double discountPercentage;

	private Double taxPercentage;

	private Double amountTax;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Double amountExo;

	@Positive(message = "AMOUNT_TOTAL must be a positive number")
	private Double amountTotal;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String authorizingUser;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime authorizationDate;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String userIssued;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime dateOfIssue;

	private Long exonerationStatus;

	private Long status;

	private LocalDateTime created;

	private String inventoryType;

	private String subWarehouse;

	private Long channel;

	private Double exemptAmount;

	private String customerAddress;

	private String customerRtnId;

	private String sellerName;

	private List<InvoiceDetailEntity> invoiceDetails;

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

	
	
}
