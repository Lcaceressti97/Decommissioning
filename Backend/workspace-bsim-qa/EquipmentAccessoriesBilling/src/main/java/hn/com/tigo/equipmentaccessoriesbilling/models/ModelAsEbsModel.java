package hn.com.tigo.equipmentaccessoriesbilling.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelAsEbsModel {

	private Long id;

	@NotNull(message = "CODMOD cannot be null")
	@NotBlank(message = "CODMOD cannot be blank")
	private String codMod;

	@NotNull(message = "CODEBS cannot be null")
	@NotBlank(message = "CODEBS cannot be blank")
	private String codEbs;

	private String subBod;

	private String newMod;

	@NotNull(message = "NAME cannot be null")
	@NotBlank(message = "NAME cannot be blank")
	private String name;


}
