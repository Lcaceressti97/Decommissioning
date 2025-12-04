package hn.com.tigo.equipmentinsurance.models;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceMasterModel {

	private Long id;

	private String inventoryType;

	@NotNull(message = "Model is required")
	@Size(min = 1, max = 6, message = "size must be between 1 and 6")	
	private String model;

	@NotNull(message = "Base Cost is required")
	private Double baseCost;

	private Long factorCode;

	@NotNull(message = "Price is required")
	private Double price;

	private String userCreated;

	private String screen;

	private LocalDateTime createdDate;

	private String currency;

	private Double lempirasPrice;

	private Double temporaryCost;

}
