package hn.com.tigo.equipmentaccessoriesbilling.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigParametersModel {

	private Long id;
	
	private Long idApplication;

	private String parameterType;

	private String parameterName;

	private String parameterDescription;

	private String parameterValue;

	private Long stateCode;

	private LocalDateTime created;

}
