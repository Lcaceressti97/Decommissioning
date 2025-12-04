package hn.com.tigo.equipmentaccessoriesbilling.models;

import java.time.LocalDateTime;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceMasterModel {

	private Long id;

	@NotNull(message = "Inventoty Type is required")
	private String inventoryType;

	@NotNull(message = "Model is required")
	private String model;

	@NotNull(message = "Description is required")
	private String description;

	@NotNull(message = "Base Cost is required")
	@DecimalMax(value = "9999999999999.9999")
	@DecimalMin(value = "0.00", message = "Base Cost must be a positive number")
	private Double baseCost;

	private Long factorCode;

	@NotNull(message = "Price is required")
	@DecimalMax(value = "9999999999999.9999")
	@DecimalMin(value = "0.00", message = "Price must be a positive number")
	private Double price;

	private String userCreated;

	private String screen;

	private LocalDateTime created;

	private String currency;

	private String convertLps;

	@NotNull(message = "Price Lps is required")
	@DecimalMax(value = "9999999999999.9999")
	@DecimalMin(value = "0.00", message = "Price Lps must be a positive number")
	private Double priceLps;

	@DecimalMax(value = "9999999999999.9999")
	@DecimalMin(value = "0.00", message = "Last Cost must be a positive number")
	private Double lastCost;

	@DecimalMax(value = "9999999999999.9999")
	@DecimalMin(value = "0.00", message = "Cost Temporary must be a positive number")
	private Double costTemporary;

	private String priceChangeEsn;

	private String esn;

	private Double priceEsn;

	private Double priceLpsEsn;

}
