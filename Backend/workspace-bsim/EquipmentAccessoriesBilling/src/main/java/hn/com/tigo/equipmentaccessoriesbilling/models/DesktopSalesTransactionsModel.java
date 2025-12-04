package hn.com.tigo.equipmentaccessoriesbilling.models;

import java.time.LocalDateTime;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DesktopSalesTransactionsModel {

	private Long id;

	@NotNull(message = "Location cannot be null")
	@Min(value = 1, message = "Location must be greater than or equal to 1")
	@Max(value = 2, message = "Location must be less than or equal to 2")
	private Long location;

	private LocalDateTime periodDate;

	@NotNull(message = "Agency cannot be null")
	private Long agency;

	@NotNull(message = "Annexed cannot be null")
	private Long annexed;

	@NotNull(message = "Seller cannot be null")
	private Long seller;

	@NotNull(message = "Charge amount cannot be null")
	@DecimalMin(value = "0.01", message = "Charge amount must be greater than 0")
	private Double chargeAmount;

	@NotNull(message = "Payment amount cannot be null")
	@DecimalMin(value = "0.01", message = "Payment amount must be greater than 0")
	private Double paymentAmount;

	private LocalDateTime closingDate;

	@NotNull(message = "STS cannot be null")
	@Pattern(regexp = "[A-Za-z]", message = "STS must be a single character")
	private String sts;
}
