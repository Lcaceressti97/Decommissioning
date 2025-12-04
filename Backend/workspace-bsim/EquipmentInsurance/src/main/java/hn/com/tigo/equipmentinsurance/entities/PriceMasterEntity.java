package hn.com.tigo.equipmentinsurance.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.equipmentinsurance.models.PriceMasterModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "SE_PRICE_MASTER")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceMasterEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "INVENTORY_TYPE", length = 1)
	private String inventoryType;

	@Column(name = "MODEL", length = 6)
	private String model;

	@Column(name = "BASE_COST")
	private Double baseCost;

	@Column(name = "FACTOR_CODE")
	private Long factorCode;

	@Column(name = "PRICE")
	private Double price;

	@Column(name = "USER_CREATED", length = 10)
	private String userCreated;

	@Column(name = "SCREEN", length = 10)
	private String screen;

	@Column(name = "CREATED_DATE")
	private LocalDateTime createdDate;

	@Column(name = "CURRENCY", length = 10)
	private String currency;

	@Column(name = "LEMPIRAS_PRICE")
	private Double lempirasPrice;

	@Column(name = "TEMPORARY_COST")
	private Double temporaryCost;

	public PriceMasterModel entityToModel() {
		PriceMasterModel model = new PriceMasterModel();
		model.setId(this.getId());
		model.setInventoryType(this.getInventoryType());
		model.setModel(this.getModel());
		model.setBaseCost(this.getBaseCost());
		model.setFactorCode(this.getFactorCode());
		model.setPrice(this.getPrice());
		model.setUserCreated(this.getUserCreated());
		model.setScreen(this.getScreen());
		model.setCreatedDate(this.getCreatedDate());
		model.setCurrency(this.getCurrency());
		model.setLempirasPrice(this.getLempirasPrice());
		model.setTemporaryCost(this.getTemporaryCost());
		return model;
	}

}
