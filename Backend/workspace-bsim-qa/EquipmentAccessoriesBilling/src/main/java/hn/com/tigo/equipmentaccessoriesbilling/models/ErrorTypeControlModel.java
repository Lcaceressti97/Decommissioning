package hn.com.tigo.equipmentaccessoriesbilling.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorTypeControlModel {

	private Long id;
	
	private Long typeError;
	
	private String description;
	
	private LocalDateTime created;
}
