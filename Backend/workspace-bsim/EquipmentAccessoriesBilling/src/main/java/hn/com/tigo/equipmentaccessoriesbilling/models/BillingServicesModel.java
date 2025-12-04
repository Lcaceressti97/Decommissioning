package hn.com.tigo.equipmentaccessoriesbilling.models;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillingServicesModel {

	private Long id;

	@NotNull(message = "SERVICE_CODE cannot be null")
	private Long serviceCode;

	@NotNull(message = "SERVICE_NAME cannot be null")
	@NotBlank(message = "SERVICE_NAME cannot be blank")
	private String serviceName;

	@NotNull(message = "TOTAL_QUANTITY cannot be null")
	private Double totalQuantity;

	private String creationUser;

	private LocalDateTime creationDate;

	private String modificationUser;

	private LocalDateTime modificationDate;

	private Long status;
}
