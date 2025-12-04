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
public class SafeControlEquipmentModel {

	private Long id;

	@NotNull
	@Size(min = 1, max = 30, message = "size must be between 1 and 30")
	private String subscriberId;

	@NotNull
	@Size(min = 1, max = 15, message = "size must be between 1 and 15")
	private String esn;

	@NotNull
	@Size(min = 1, max = 30, message = "size must be between 1 and 30")
	private String customerAccount;

	@NotNull
	@Size(min = 1, max = 30, message = "size must be between 1 and 30")
	private String serviceAccount;

	@NotNull
	@Size(min = 1, max = 30, message = "size must be between 1 and 30")
	private String billingAccount;

	@NotNull
	@Size(min = 1, max = 12, message = "size must be between 1 and 12")
	private String phone;

	private String phoneModel;

	private String originPhone;

	private String inventoryType;

	private String originType;

	private LocalDateTime dateStartService;

	private LocalDateTime dateInit;

	private LocalDateTime dateEnd;

	private LocalDateTime dateInclusion;

	private Long monthlyFee;

	private Long currentPeriod;

	private Long insuranceStatus;

	private String userAs400;

	private LocalDateTime dateTransaction;

	private String operationProgram;

	private Long period2;

	private Long monthlyFee2;

	private Long period3;

	private Long monthlyFee3;

}
