package hn.com.tigo.equipmentblacklist.models;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImeiControlFileModel {

	@JsonIgnore
	private Long id;

	private String transactionId;

	@NotNull(message = "IMEI cannot be null")
	@NotBlank(message = "The PHONE is required.")
	@Size(min = 8, max = 20, message = "The PHONE must be from 8 to 20 characters.")
	private String phone;

	@NotNull(message = "IMEI cannot be null")
	@NotBlank(message = "The IMEI is required.")
	@Size(max = 50, message = "The IMEI must be 50 characters.")
	private String imei;

	private String imsi;

	private Long status;

	private LocalDateTime createdDate;

}
