package hn.com.tigo.selfconsumption.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParamAutoconsumoHistModel {

	
	private Long id;
	
	private Long idParameter;

	private String name;

	private String value;

	private String description;
	
	private String userName;

	private Long status;

	private LocalDateTime createDate;
}
