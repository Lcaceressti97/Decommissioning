package hn.com.tigo.equipmentinsurance.models;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReasonsModel {

	private Long id;

	@NotNull(message = "REASON cannot be null")
	@NotBlank(message = "The REASON is required.")
	private String reason;

	private String description;

	private Long status;

	private LocalDateTime createdDate;

}
