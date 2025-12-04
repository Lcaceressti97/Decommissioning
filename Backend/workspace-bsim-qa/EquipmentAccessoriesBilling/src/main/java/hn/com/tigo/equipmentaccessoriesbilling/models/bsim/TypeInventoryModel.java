package hn.com.tigo.equipmentaccessoriesbilling.models.bsim;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeInventoryModel {

	private Long id;

	private BigDecimal version;

	private String type;

	private String longDescription;

	private String shortDescription;

	private String externalCode;

}
