package hn.com.tigo.inquiriesamsysnavega.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NavegaPaymentsModel {

	private Long id;

	@NotNull(message = "Navega Code is required")
	private String navegaCode;

	@NotNull(message = "Product Code is required")
	private Integer productCode;

	@NotNull(message = "Currency is required")
	private String currency;

	@NotNull(message = "Payment Amout is required")
	@DecimalMax(value = "9999999999999.99")
	private Double paymentAmount;

	@NotNull(message = "Bank is required")
	private String bank;

	@NotNull(message = "Bank Acct is required")
	private String bankAcct;

	@NotNull(message = "Bank Authorization is required")
	@DecimalMax(value = "99999999")
	private Long bankAuthorization;

	@DecimalMax(value = "9999999999999.9999")
	private Double exchangeRate;

	private LocalDateTime paymentDate;

	private Integer idOrganization;

	@NotNull(message = "EBS Account is required")
	private String ebsAccount;

	private String transactionSts;

	private String ouName;

	private String synchron;

	private String recMethod;

	private LocalDateTime receiptDate;

	private LocalDateTime glDate;

	private String recNumber;

	private Integer recAmount;
	
	private String custAcctNum;

	private String comments;

	private String attributeCat;

	private String attribute1;

	private String attribute2;

	private String attribute3;
	
	private BigDecimal receiptNumber;


}
