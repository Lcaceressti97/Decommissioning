package hn.com.tigo.jteller.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "MEA_BILLING")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "INVOICE_NO", length = 100)
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
	
	@Column(name = "XML", length = 100)
	private String xml;

	@Column(name = "CAI", length = 100)
	private String cai;

	@Column(name = "CUSTOMER", length = 100)
	private String customer;

	@Column(name = "SELLER", length = 100)
	private String seller;
	
	@Column(name = "CASHIER_NAME", length = 100)
	private String cashierName;
	
	@Column(name = "WAREHOUSE", length = 100)
	private String warehouse;

	@Column(name = "AGENCY", length = 100)
	private String agency;
		
	@Column(name = "DUE_DATE")
	private LocalDateTime dueDate;

	@Column(name = "INITIAL_RANK", length = 150)
	private String initialRank;

	@Column(name = "FINAL_RANK", length = 150)
	private String finalRank;

	@Column(name = "SUBTOTAL")
	private Double subtotal;
	
	@Column(name = "EXCHANGE_RATE")
	private Double exchangeRate;

	@Column(name = "DISCOUNT")
	private Double discount;

	@Column(name = "DISCOUNT_PERCENTAGE")
	private Double discountPercentage;

	@Column(name = "TAX")
	private Double tax;

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
	
	@Column(name = "ID_BRANCH_OFFICES")
	private Long idBranchOffices;
	
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
	
	@Column(name = "CUSTOMER_ID", length = 50)
	private String customerId;
	
}
