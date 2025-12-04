package hn.com.tigo.equipmentaccessoriesbilling.models;

import hn.com.tigo.equipmentaccessoriesbilling.entities.PriceMasterEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceMasterDTO {

	private String model;

	private String description;

	private String inventoryType;

	private Double baseCost;

	private Long factorCode;

	private Double price;

	private String convertLps;

	private Double priceLps;

	private Double lastCost;

	public PriceMasterDTO(PriceMasterEntity entity) {
		this.model = entity.getModel();
		this.description = entity.getDescription();
		this.inventoryType = entity.getInventoryType();
		this.baseCost = entity.getBaseCost();
		this.factorCode = entity.getFactorCode();
		this.price = entity.getPrice();
		this.convertLps = entity.getConvertLps();
		this.priceLps = entity.getPriceLps();
		this.lastCost = entity.getLastCost();
	}
}
