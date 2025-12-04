package hn.com.tigo.selfconsumption.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParametersAutoconsumoModel {

	private Long id;
	
	private Long idApplication;

	private String name;

	private String value;

	private String description;

	private Long status;

	private LocalDateTime createdDate;

}
