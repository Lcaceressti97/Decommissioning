package hn.com.tigo.simcardinquiry.models;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDetailModel {

	private Long id;

	private Long idPrefecture;

	@NotNull(message = "MODEL cannot be null")
	@NotBlank(message = "MODEL cannot be blank")
	private String model;

	private String description;

	@NotNull(message = "QUANTITY cannot be null")
	@Positive(message = "QUANTITY must be a positive number")
	private Double quantity;

	@NotNull(message = "UNIT_PRICE cannot be null")
	@Positive(message = "UNIT_PRICE must be a positive number")
	private Double unitPrice;

	@NotNull(message = "AMOUNT_TOTAL cannot be null")
	@Positive(message = "AMOUNT_TOTAL must be a positive number")
	private Double amountTotal;

	private String serieOrBoxNumber;

	private Long status;

	private LocalDateTime created;
}
