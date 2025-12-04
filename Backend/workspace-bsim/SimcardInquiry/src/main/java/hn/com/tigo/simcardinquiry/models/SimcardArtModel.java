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
public class SimcardArtModel {

	private Long id;

	@NotNull(message = "Art Number is required")
	private String artNumber;

	@NotBlank(message = "Art Description  is required")
	private String artDescription;

	private Long status;

	private LocalDateTime createdDate;
}
