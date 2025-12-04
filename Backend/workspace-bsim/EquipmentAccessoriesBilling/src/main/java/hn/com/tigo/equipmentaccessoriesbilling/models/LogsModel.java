package hn.com.tigo.equipmentaccessoriesbilling.models;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogsModel {

	private Long id;

	private LocalDateTime created;

	@NotNull(message = "TYPE_ERROR cannot be null")
	private Long typeError;

	@NotBlank(message = "MESSAGE cannot be blank")
	private String message;

	private Long reference;

	private String srt;
	
	@NotBlank(message = "URL cannot be blank")
	private String url;
	
	private String userCreate;

	private Long executionTime;

}
