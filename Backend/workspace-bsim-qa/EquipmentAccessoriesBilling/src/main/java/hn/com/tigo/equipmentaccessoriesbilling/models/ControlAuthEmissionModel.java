package hn.com.tigo.equipmentaccessoriesbilling.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ControlAuthEmissionModel {

	private Long id;

	@NotNull(message = "ID_PREFECTURE cannot be null")
	private Long idPrefecture;

	@NotNull(message = "TYPE_APPROVAL cannot be null")
	private Long typeApproval;

	@NotNull(message = "DESCRIPTION cannot be null")
	@NotBlank(message = "DESCRIPTION cannot be blank")
	private String description;

	@NotNull(message = "USER_CREATE cannot be null")
	@NotBlank(message = "USER_CREATE cannot be blank")
	private String userCreate;
	
	private String paymentCode;

	private Long idBranchOffices;

	private LocalDateTime created;
}
