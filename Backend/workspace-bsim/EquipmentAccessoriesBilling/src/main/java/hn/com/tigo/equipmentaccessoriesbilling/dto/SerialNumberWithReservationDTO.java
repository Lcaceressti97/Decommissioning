package hn.com.tigo.equipmentaccessoriesbilling.dto;

import lombok.Data;

@Data
public class SerialNumberWithReservationDTO {
	
	String itemCode;
	String warehouseCode;
	String subWarehouseCode; 
	String inventoryType;
	Integer rowLimit;
	
}
