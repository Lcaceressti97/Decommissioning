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
public class DeductibleRatesModel {

	private Long id;

	@NotNull(message = "Model is required")
	@NotBlank(message = "Model is required")
	private String model;

	@NotNull(message = "Description is required")
	@NotBlank(message = "Description is required")
	private String description;

	@NotNull(message = "First Claim is required")
	@NotBlank(message = "First Claim is required")
	private String firstClaim;

	@NotNull(message = "Second Claim is required")
	@NotBlank(message = "Second Claim is required")
	private String secondClaim;

	@NotNull(message = "Third Claim is required")
	@NotBlank(message = "Third Claim is required")
	private String thirdClaim;

    @NotNull(message = "Reason is required")
    private Long reason;
    
    private String reasonDescription;
    
	private Long status;

	private LocalDateTime created;
}
