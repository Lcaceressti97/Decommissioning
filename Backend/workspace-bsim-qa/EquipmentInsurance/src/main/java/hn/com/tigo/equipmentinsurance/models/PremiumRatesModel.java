package hn.com.tigo.equipmentinsurance.models;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PremiumRatesModel {

	private Long id;

	@NotNull(message = "Model is required")
	@NotBlank(message = "Model is required")
	private String model;

	@NotNull(message = "Device Value is required")
	@NotBlank(message = "Device Value is required")
	private String deviceValue;

	@NotNull(message = "Monthly Premium is required")
	@NotBlank(message = "Monthly Premium is required")
	private String monthlyPremium;

	private Long status;

	private LocalDateTime created;

}
