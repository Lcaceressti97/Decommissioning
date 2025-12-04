package hn.com.tigo.equipmentaccessoriesbilling.models.bsim;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvSubWarehouseModel {
	
	private Long id;

	private BigDecimal version;

	private String code;

	private String name;

}
