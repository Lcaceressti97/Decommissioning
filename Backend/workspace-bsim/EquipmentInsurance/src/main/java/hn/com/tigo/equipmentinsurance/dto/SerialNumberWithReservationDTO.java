package hn.com.tigo.equipmentinsurance.dto;

import lombok.Data;

@Data
public class SerialNumberWithReservationDTO {
	
	String itemCode;
	String warehouseCode;
	String subWarehouseCode; 
	String inventoryType;
	Integer rowLimit;
	
}
