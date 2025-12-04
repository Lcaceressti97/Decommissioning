package hn.com.tigo.equipmentaccessoriesbilling.models;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelInvoiceWithoutFiscalNoModel {

	private Long id;

	@NotNull(message = "ID_USER cannot be null")
	private Long idUser;

	@NotNull(message = "The USER_NAME is required.")
	@NotBlank(message = "USER_NAME cannot be blank")
	private String userName;

	@NotNull(message = "The PERMIT_STATUS is required.")
	@NotBlank(message = "PERMIT_STATUS cannot be blank")
	private String permitStatus;

	private LocalDateTime created;
}
