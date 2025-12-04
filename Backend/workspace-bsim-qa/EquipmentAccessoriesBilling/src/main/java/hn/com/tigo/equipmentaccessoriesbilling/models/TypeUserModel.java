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
public class TypeUserModel {

	private Long id;

	@NotNull(message = "TYPE USER cannot be null")
	@NotBlank(message = "TYPE USER cannot be blank")
	private String typeUser;

	@NotNull(message = "DESCRIPTION cannot be null")
	@NotBlank(message = "DESCRIPTION cannot be blank")
	private String description;

	private Long status;

	private LocalDateTime created;

}
