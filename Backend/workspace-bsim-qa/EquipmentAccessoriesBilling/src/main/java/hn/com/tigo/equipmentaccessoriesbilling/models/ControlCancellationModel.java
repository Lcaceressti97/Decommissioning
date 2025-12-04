package hn.com.tigo.equipmentaccessoriesbilling.models;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ControlCancellationModel {

	private Long id;

	@NotNull(message = "ID_PREFECTURE cannot be null")
	private Long idPrefecture;

	@NotNull(message = "TYPE_CANCELLATION cannot be null")
	private Long typeCancellation;

	@NotNull(message = "DESCRIPTION cannot be null")
	@NotBlank(message = "DESCRIPTION cannot be blank")
	private String description;

	@NotNull(message = "USER_CREATE cannot be null")
	@NotBlank(message = "USER_CREATE cannot be blank")
	private String userCreate;

	private LocalDateTime created;
	
	private String warehouse;

	private String inventoryType;

	private String cancelReason;
}
