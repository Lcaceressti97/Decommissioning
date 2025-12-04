package hn.com.tigo.simcardinquiry.models;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimcardModelModel {

	private Long id;

	@NotBlank(message = "SIM Model is required")
	private String simModel;

	@NotBlank(message = "Model Description  is required")
	private String modelDescription;

	private Long status;

	private LocalDateTime createdDate;

}
