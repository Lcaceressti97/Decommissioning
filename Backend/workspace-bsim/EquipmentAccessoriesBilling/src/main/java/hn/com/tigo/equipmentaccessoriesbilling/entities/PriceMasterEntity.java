package hn.com.tigo.equipmentaccessoriesbilling.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.equipmentaccessoriesbilling.models.PriceMasterModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "MEA_PRICE_MASTER")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceMasterEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "INVENTORY_TYPE", length = 50)
	private String inventoryType;

	@Column(name = "MODEL", length = 250)
	private String model;
	
	@Column(name = "DESCRIPTION", length = 250)
	private String description;

	@Column(name = "BASE_COST")
	private Double baseCost;

	@Column(name = "FACTOR_CODE")
	private Long factorCode;

	@Column(name = "PRICE")
	private Double price;

	@Column(name = "USER_CREATE", length = 50)
	private String userCreated;

	@Column(name = "SCREEN", length = 50)
	private String screen;

	@Column(name = "CREATED")
	private LocalDateTime created;

	@Column(name = "CURRENCY", length = 50)
	private String currency;

	@Column(name = "CONVERT_LPS", length = 50)
	private String convertLps;

	@Column(name = "PRICE_LPS")
	private Double priceLps;

	@Column(name = "LAST_COST")
	private Double lastCost;

	@Column(name = "COST_TEMPORARY")
	private Double costTemporary;

	@Column(name = "PRICE_CHANGE_ESN", length = 10)
	private String priceChangeEsn;
	
	@Column(name = "ESN")
	private String esn;

	@Column(name = "PRICE_ESN")
	private Double priceEsn;

	@Column(name = "PRICE_LPS_ESN")
	private Double priceLpsEsn;

	public PriceMasterModel entityToModel() {
		PriceMasterModel model = new PriceMasterModel();

		model.setId(this.getId());
		model.setInventoryType(this.getInventoryType());
		model.setModel(this.getModel());
		model.setDescription(this.getDescription());
		model.setBaseCost(this.getBaseCost());
		model.setFactorCode(this.getFactorCode());
		model.setPrice(this.getPrice());
		model.setUserCreated(this.getUserCreated());
		model.setScreen(this.getScreen());
		model.setCreated(this.getCreated());
		model.setCurrency(this.getCurrency());
		model.setConvertLps(this.getConvertLps());
		model.setPriceLps(this.getPriceLps());
		model.setLastCost(this.getLastCost());
		model.setCostTemporary(this.getCostTemporary());
		model.setPriceChangeEsn(this.getPriceChangeEsn());
		model.setEsn(this.getEsn());
		model.setPriceEsn(this.getPriceEsn());
		model.setPriceLpsEsn(this.getPriceLpsEsn());
		return model;
	}

}
