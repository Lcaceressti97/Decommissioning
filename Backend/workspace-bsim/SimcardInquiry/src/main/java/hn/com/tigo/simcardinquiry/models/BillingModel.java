package hn.com.tigo.simcardinquiry.models;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonInclude;

import hn.com.tigo.simcardinquiry.entities.InvoiceDetailEntity;
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
	private String invoiceType;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long idReference;

	@NotNull(message = "ACCTCODE cannot be null")
	@NotBlank(message = "ACCTCODE cannot be blank")
	private String acctCode;

	@NotNull(message = "PRIMARY_IDENTITY cannot be null")
	@NotBlank(message = "PRIMARY_IDENTITY cannot be blank")
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

	private List<InvoiceDetailEntity> invoiceDetails;

}
