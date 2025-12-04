package hn.com.tigo.simcardinquiry.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
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
