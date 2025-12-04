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
public class SimcardGraphicModel {

	private Long id;

	@NotNull(message = "Graphic Ref is required")
	private String graphicRef;

	@NotBlank(message = "Graphic Description  is required")
	private String graphicDescription;

	private Long status;

	private LocalDateTime createdDate;
}
