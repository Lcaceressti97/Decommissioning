package hn.com.tigo.equipmentinsurance.models;

import lombok.Data;

@Data
public class CalculateDeductibleRequest {
	
	private String model;
	
	private Long reasonId;
	
	private Double price;

	private int quantity;
}
