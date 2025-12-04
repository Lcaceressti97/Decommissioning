package hn.com.tigo.simcardinquiry.models;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimcardTypeModel {

	private Long id;

	@NotNull(message = "Type Number is required")
	private String typeNumber;

	@NotBlank(message = "Type Description  is required")
	private String typeDescription;

	private Long status;

	private LocalDateTime createdDate;

}
