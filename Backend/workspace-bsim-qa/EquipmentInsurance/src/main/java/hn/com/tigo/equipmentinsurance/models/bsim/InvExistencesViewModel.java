package hn.com.tigo.equipmentinsurance.models.bsim;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvExistencesViewModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal warehouseId;
	private String warehouseCode;
	private String inventoryType;
	private String lineCode;
	private String modelCode;
	private String model;
	private String description;
	private String brand;
	private BigDecimal quantity;
}
