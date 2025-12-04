package hn.com.tigo.equipmentaccessoriesbilling.models;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsuranceRatesModel {

	private Long id;

	private LocalDateTime efectiveDate;

	@NotNull(message = "Period Number is required")
	private Long periodNumber;

	@NotNull(message = "Value From is required")
	private Double valueFrom;

	@NotNull(message = "Value Up is required")
	private Double valueUp;

	@NotNull(message = "Monthly Fee is required")
	private Double monthlyFee;

	private String textCoverage;

	@NotNull(message = "Model is required")
	private String model;

}