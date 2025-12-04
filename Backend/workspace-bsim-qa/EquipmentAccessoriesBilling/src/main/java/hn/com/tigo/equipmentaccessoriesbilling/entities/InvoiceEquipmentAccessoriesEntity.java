package hn.com.tigo.equipmentaccessoriesbilling.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import hn.com.tigo.equipmentaccessoriesbilling.models.InvoiceEquipmentAccessoriesModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "MEA_HISTORICAL_INVOICE")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceEquipmentAccessoriesEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "INVOICE_NO", length = 100)
	private String invoiceNo;

	@Column(name = "INVOICE_TYPE", length = 100)
	private String invoiceType;

	@Column(name = "INVOICE_STATUS")
	private Long invoiceStatus;

	@Column(name = "BILLING_ACCOUNT", length = 100)
	private String billingAccount;

	@Column(name = "SUBSCRIBER", length = 100)
	private String subscriber;

	@Column(name = "CUSTCODE", length = 100)
	private String custcode;

	@Column(name = "EMAILADDR", length = 100)
	private String emailaddr;

	@Column(name = "COMPANY", length = 100)
	private String company;

	@Column(name = "CAI", length = 100)
	private String cai;

	@Column(name = "CUSTOMER_NAME", length = 100)
	private String customerName;

	@Column(name = "CUSTOMER_TYPE", length = 100)
	private String customerType;

	@Column(name = "DOCUMENT_NO", length = 100)
	private String documentNo;

	@Column(name = "DIPLOMATIC_CARD_NO", length = 100)
	private String diplomaticCardNo;

	@Column(name = "CORRELATIVE_ORDEN_EXEMPT_NO", length = 100)
	private String correlativeOrdenExemptNo;

	@Column(name = "CORRELATIVE_CERTIFICATE_EXO_NO", length = 100)
	private String correlativeCertificateExoNo;

	@Column(name = "EXONERATION_STATUS")
	private Long exonerationStatus;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "XML", length = 100)
	private String xml;

	@Column(name = "PATH", length = 1000)
	private String path;

	@Column(name = "ADDRESS", length = 500)
	private String address;

	@Column(name = "WAREHOUSE", length = 100)
	private String warehouse;

	@Column(name = "AGENCY", length = 100)
	private String agency;

	@Column(name = "TRANSACTION_USER", length = 100)
	private String transactionUser;

	@Column(name = "CHARGE_LOCAL", length = 100)
	private String chargeLocal;

	@Column(name = "CHARGE_USD", length = 100)
	private String chargeUsd;

	@Column(name = "EXCHANGE_RATE", length = 100)
	private String exchangeRate;

	@Column(name = "TAX", length = 100)
	private String tax;

	@Column(name = "DISCOUNT", length = 100)
	private String discount;

	@Column(name = "CREATED")
	private LocalDateTime created;

	@Column(name = "CREATED_BY", length = 50)
	private String createdBy;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = InvoiceStatusEntity.class, optional = false)
	@JoinColumn(name = "INVOICE_STATUS", referencedColumnName = "ID_STATUS", nullable = false, updatable = false, insertable = false)
	private InvoiceStatusEntity invoiceStatusEntity;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = ExemptionStatusEntity.class, optional = false)
	@JoinColumn(name = "EXONERATION_STATUS", referencedColumnName = "ID_STATUS", nullable = false, updatable = false, insertable = false)
	private ExemptionStatusEntity exemptionStatusEntity;

	public InvoiceEquipmentAccessoriesModel entityToModel() {
		InvoiceEquipmentAccessoriesModel model = new InvoiceEquipmentAccessoriesModel();
		model.setId(this.getId());
		model.setInvoiceNo(this.getInvoiceNo());
		model.setInvoiceType(this.getInvoiceType());
		model.setInvoiceStatus(this.getInvoiceStatus());
		model.setInvoiceStatusName(this.getInvoiceStatusEntity().getNameStatus());
		model.setBillingAccount(this.getBillingAccount());
		model.setSubscriber(this.getSubscriber());
		model.setCustcode(this.getCustcode());
		model.setEmailaddr(this.getEmailaddr());
		model.setCompany(this.getCompany());
		model.setCai(this.getCai());
		model.setCustomerName(this.getCustomerName());
		model.setCustomerType(this.getCustomerType());
		model.setDocumentNo(this.getDocumentNo());
		model.setDiplomaticCardNo(this.getDiplomaticCardNo());
		model.setCorrelativeOrdenExemptNo(this.getCorrelativeOrdenExemptNo());
		model.setCorrelativeCertificateExoNo(this.getCorrelativeCertificateExoNo());
		model.setExonerationStatus(this.getExonerationStatus());
		if (this.getExemptionStatusEntity() != null) {
			model.setExonerationStatusName(this.getExemptionStatusEntity().getNameStatus());
		}
		model.setXml(this.getXml());
		model.setPath(this.getPath());
		model.setAddress(this.getAddress());
		model.setWarehouse(this.getWarehouse());
		model.setAgency(this.getAgency());
		model.setTransactionUser(this.getTransactionUser());
		model.setChargeLocal(this.getChargeLocal());
		model.setChargeUsd(this.getChargeUsd());
		model.setExchangeRate(this.getExchangeRate());
		model.setTax(this.getTax());
		model.setDiscount(this.getDiscount());
		model.setCreated(this.getCreated());
		model.setCreatedBy(this.getCreatedBy());
		return model;
	}

}
