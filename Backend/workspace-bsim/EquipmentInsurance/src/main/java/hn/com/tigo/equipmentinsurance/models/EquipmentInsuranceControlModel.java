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
public class EquipmentInsuranceControlModel {

	private Long id;

	@NotNull(message = "Transaction Code is required")
	private String transactionCode;

	private String userAs;

	private LocalDateTime dateConsultation;

	@NotNull(message = "Customer Account is required")
	@Size(min = 1, max = 30, message = "size must be between 1 and 30")
	private String customerAccount;

	@NotNull(message = "Service Account is required")
	@Size(min = 1, max = 30, message = "size must be between 1 and 30")
	private String serviceAccount;

	@NotNull(message = "Billing Account is required")
	@Size(min = 1, max = 30, message = "size must be between 1 and 30")
	private String billingAccount;
	
	@NotNull(message = "Phone Number is required")
	@Size(min = 1, max = 12, message = "size must be between 1 and 12")
	private String phoneNumber;
	
	@NotNull(message = "Equipment Model is required")
	private String equipmentModel;

	@NotNull(message = "ESN is required")
	@Size(min = 1, max = 15, message = "size must be between 1 and 15")
	private String esn;
	
	private String originAs;

	private String inventoryTypeAs;

	private String originTypeAs;

	private LocalDateTime dateContract;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

	private LocalDateTime dateInclusion;

	private Double insuranceRate;

	private Double period;

	private Double insuranceRate2;

	private Double period2;

	private Double insuranceRate3;

	private Double period3;

	private Long insuranceStatus;

	@NotNull(message = "Subscriber is required")
	@Size(min = 1, max = 30, message = "size must be between 1 and 30")
	private String subscriber;

	private String trama;


}
