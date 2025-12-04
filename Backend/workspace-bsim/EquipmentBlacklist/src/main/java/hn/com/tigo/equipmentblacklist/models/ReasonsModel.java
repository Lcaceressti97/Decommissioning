package hn.com.tigo.equipmentblacklist.models;

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

	@NotNull(message = "REASONS cannot be null")
	@NotBlank(message = "The REASONS is required.")
	private String reasons;

	private Long status;

	private LocalDateTime createdDate;

}
