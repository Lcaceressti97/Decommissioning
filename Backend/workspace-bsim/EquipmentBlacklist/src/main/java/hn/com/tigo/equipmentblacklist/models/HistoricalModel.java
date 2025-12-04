package hn.com.tigo.equipmentblacklist.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoricalModel {

	private Long id;

	@NotNull(message = "ESN cannot be null")
	@NotBlank(message = "The ESN is required.")
	private String esn;

	@NotNull(message = "ACCT_CODE cannot be null")
	private Long acctCode;

	@NotNull(message = "PHONE cannot be null")
	@NotBlank(message = "The PHONE is required.")
	@Size(min = 8, max = 20, message = "The PHONE must be from 8 to 20 characters.")
	private String phone;

	@NotNull(message = "LOCK_TYPE cannot be null")
	@NotBlank(message = "The LOCK_TYPE is required.")
	private String lockType;

	private Long status;

	private LocalDateTime createdDate;

	private LocalDateTime processDate;

}
