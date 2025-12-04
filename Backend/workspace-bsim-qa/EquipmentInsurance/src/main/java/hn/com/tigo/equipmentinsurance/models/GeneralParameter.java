package hn.com.tigo.equipmentinsurance.models;

import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GeneralParameter {

	@NonNull
	private String name;

	@NonNull
	private String value;

	@NonNull
	private String description;

	@NonNull
	private String modifiedBy;

	private String modifiedAt;

	private String createdBy;
}
