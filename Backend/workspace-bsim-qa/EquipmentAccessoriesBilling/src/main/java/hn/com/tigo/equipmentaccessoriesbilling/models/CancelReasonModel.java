package hn.com.tigo.equipmentaccessoriesbilling.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelReasonModel {
	
	private Long id;
	
	private Long reasonCode;
	
	private String reasonDesc;
	
	private String inventoryType;
	
	private Long status;

	private LocalDateTime created;

}
