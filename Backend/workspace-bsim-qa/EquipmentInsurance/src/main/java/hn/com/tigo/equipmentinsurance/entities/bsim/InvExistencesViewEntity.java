package hn.com.tigo.equipmentinsurance.entities.bsim;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.equipmentinsurance.models.bsim.InvExistencesViewModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "VIEW_INV_EXISTENCES", schema = "STCINVENTORY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvExistencesViewEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "WAREHOUSE_ID", nullable = false, updatable = false)
	private BigDecimal warehouseId;

	@Column(name = "WAREHOUSE_CODE")
	private String warehouseCode;

	@Column(name = "INVENTORY_TYPE")
	private String inventoryType;

	@Column(name = "LINE_CODE")
	private String lineCode;

	@Column(name = "MODEL_CODE")
	private String modelCode;

	@Column(name = "MODEL")
	private String model;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "BRAND")
	private String brand;

	@Column(name = "QUANTITY")
	private BigDecimal quantity;

	public InvExistencesViewModel entityToModel() {
		InvExistencesViewModel invExistencesViewModel = new InvExistencesViewModel();

		invExistencesViewModel.setWarehouseId(warehouseId);
		invExistencesViewModel.setWarehouseCode(warehouseCode);
		invExistencesViewModel.setInventoryType(inventoryType);
		invExistencesViewModel.setLineCode(lineCode);
		invExistencesViewModel.setModelCode(modelCode);
		invExistencesViewModel.setModel(model);
		invExistencesViewModel.setDescription(description);
		invExistencesViewModel.setBrand(brand);
		invExistencesViewModel.setQuantity(quantity);

		return invExistencesViewModel;
	}
}
