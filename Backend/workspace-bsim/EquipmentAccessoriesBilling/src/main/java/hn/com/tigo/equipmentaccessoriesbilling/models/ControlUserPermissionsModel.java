package hn.com.tigo.equipmentaccessoriesbilling.models;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControlUserPermissionsModel {

	private Long id;

	@NotNull(message = "The ID_USER is required.")
	private Long idUser;

	@NotNull(message = "The NAME is required.")
	@NotBlank(message = "NAME cannot be blank")
	private String name;

	@NotNull(message = "The USER_NAME is required.")
	@NotBlank(message = "USER_NAME cannot be blank")
	private String userName;

	@NotNull(message = "The EMAIL is required.")
	@NotBlank(message = "EMAIL cannot be blank")
	@Email(message = "The EMAIL is not a valid email.")
	private String email;

	@NotNull(message = "The GENERATE_BILL is required.")
	@NotBlank(message = "GENERATE_BILL cannot be blank")
	private String generateBill;

	@NotNull(message = "The ISSUE_SELLER_INVOICE is required.")
	@NotBlank(message = "ISSUE_SELLER_INVOICE cannot be blank")
	private String issueSellerInvoice;

	@NotNull(message = "The AUTHORIZE_INVOICE is required.")
	@NotBlank(message = "AUTHORIZE_INVOICE cannot be blank")
	private String authorizeInvoice;

	@NotNull(message = "TYPE USER cannot be null")
	private Long typeUser;
	
	@NotNull(message = "The ASSIGN_DISCOUNT is required.")
	@NotBlank(message = "ASSIGN_DISCOUNT cannot be blank")
	private String assignDiscount;

	private LocalDateTime created;

}
