package hn.com.tigo.equipmentinsurance.models;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
	@Pattern(regexp = "^\\w+$", message = "Model must not contain spaces")
	private String model;

	private String description;

	@NotNull(message = "QUANTITY cannot be null")
	@Positive(message = "QUANTITY must be a positive number")
	private Double quantity;

	@NotNull(message = "UNIT_PRICE cannot be null")
	@Positive(message = "UNIT_PRICE must be a positive number")
	private Double unitPrice;

	@Positive(message = "AMOUNT_TOTAL must be a positive number")
	private Double subtotal;

	private Double discount;

	@Positive(message = "AMOUNT_TOTAL must be a positive number")
	private Double tax;

	@Positive(message = "AMOUNT_TOTAL must be a positive number")
	private Double amountTotal;

	private String serieOrBoxNumber;

	private Long status;

	private LocalDateTime created;
}
