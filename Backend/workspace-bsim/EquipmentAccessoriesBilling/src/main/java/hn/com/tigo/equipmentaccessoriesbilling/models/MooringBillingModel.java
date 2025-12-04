package hn.com.tigo.equipmentaccessoriesbilling.models;

import java.time.LocalDateTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MooringBillingModel {

	// Props
	private Long id;

	@NotNull(message = "cannot be null")
	@Positive(message = "must be a positive number")
	@Max(value = 99999999L, message = "maximum value allowed is up to 99999999")
	private Long correlativeCmd;

	@NotNull(message = "cannot be null")
	@Positive(message = "must be a positive number")
	@Max(value = 999L, message = "maximum value allowed is up to 999")
	private Long correlativeMooringCmd;

	@NotNull(message = "cannot be null")
	@Positive(message = "must be a positive number")
	@Pattern(regexp = "\\d+", message = "must be a number")
	private String subscriberId;

	@NotNull(message = "cannot be null")
	@Positive(message = "must be a positive number")
	private int monthsOfPermanence;

	private String cmdStatus;

	private String userName;

	private String supervisorUser;

	private String userMooring;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String userCancelled;

	private LocalDateTime dateOfAdmission;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime dueDate;

	private Long invoiceLocation;

	private Long invoiceSubLocity;

	private String invoiceType;

	private String invoiceReading;

	private String invoiceNumber;

	private String inventoryType;

	private String inventoryModel;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String teamSeries;

	private Double phoneCost;

	private Double phoneDiscount;

	@NotNull(message = "cannot be null")
	private Double vac;

	private Long mooring;

	private String promotion;

	private String transactionId;

	@NotNull(message = "cannot be null")
	private String customerAccount;

	private String serviceAccount;

	@NotNull(message = "cannot be null")
	private String billingAccount;

	private String observations;

	private LocalDateTime createDate;

}
