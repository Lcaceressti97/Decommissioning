package hn.com.tigo.simcardinquiry.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigParameterModel {

	private Long id;
	
	private Long idApplication;

	private String parameterType;

	private String parameterName;

	private String parameterDescription;

	private String parameterValue;

	private Long stateCode;

	private LocalDateTime created;

}
