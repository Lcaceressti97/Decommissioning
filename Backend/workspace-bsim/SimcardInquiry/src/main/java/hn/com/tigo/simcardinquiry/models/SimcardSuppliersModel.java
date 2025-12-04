package hn.com.tigo.simcardinquiry.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimcardSuppliersModel {

	private Long id;

	private Long supplierNo;

	@NotBlank(message = "Supplier name is required")
	private String supplierName;

	@NotBlank(message = "Address is required")
	private String address;

	@NotBlank(message = "Phone is required")
	@Size(max = 20, message = "Phone must be at most 20 characters")
	private String phone;

	@NotBlank(message = "Postal Mail is required")
	private String postalMail;

	@Email(message = "Invalid email supplier format")
	private String emailSupplier;

	@Email(message = "Invalid email format")
	private String email;

	private String baseFile;

	@NotBlank(message = "Subject is required")
	private String subject;

	@NotBlank(message = "Text Email is required")
	private String textEmail;

	private String initialIccd;

	@NotBlank(message = "Key is required")
	private String key;

	private Long status;

	private LocalDateTime createdDate;

}
