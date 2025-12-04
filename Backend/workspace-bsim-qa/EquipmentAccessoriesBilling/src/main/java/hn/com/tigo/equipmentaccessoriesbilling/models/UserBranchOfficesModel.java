package hn.com.tigo.equipmentaccessoriesbilling.models;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBranchOfficesModel {

	private Long id;

	@NotNull(message = "ID_USER cannot be null")
	private Long idUser;

	@NotNull(message = "ID_BRANCH_OFFICES cannot be null")
	private Long idBranchOffices;

	private Long status;

	private LocalDateTime created;
}
