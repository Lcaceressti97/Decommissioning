package hn.com.tigo.equipmentaccessoriesbilling.models.bsim;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvModelsOrArticlesModel {

	private Long id;

	private String code;

	private Long equipmentLineId;

	private Long equipmentGroupId;

	private String model;

	private String description;

	private Long version;

	private Long accAccountId;

	private Long brandId;

}
