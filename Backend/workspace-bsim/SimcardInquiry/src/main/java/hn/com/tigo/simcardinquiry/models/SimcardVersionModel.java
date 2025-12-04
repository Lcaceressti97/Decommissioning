package hn.com.tigo.simcardinquiry.models;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimcardVersionModel {

	private Long id;

	@NotNull(message = "ID Model is required")
	private Long idModel;

	@NotNull(message = "Version is required")
	private String version;

	@NotNull(message = "Version Size is required")
	private String versionSize;

	private String versionDescription;

	@NotNull(message = "Capacity is required")
	private String capacity;
	
	@NotNull(message = "Version Detail is required")
	private String versionDetail;

	private Long status;

	private LocalDateTime createdDate;
}
