package hn.com.tigo.equipmentaccessoriesbilling.models;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchOfficesModel {

	private Long id;

	private Long idPoint;

	@NotNull(message = "NAME cannot be null")
	@NotBlank(message = "NAME cannot be blank")
	private String name;

	@NotNull(message = "ADDRESS cannot be null")
	@NotBlank(message = "ADDRESS cannot be blank")
	private String address;

	@NotNull(message = "PHONE cannot be null")
	@NotBlank(message = "PHONE cannot be blank")
	@Size(min = 8, max = 20, message = "The PHONE must be from 8 to 20 characters.")
	private String phone;

	@NotNull(message = "RTN cannot be null")
	@NotBlank(message = "RTN cannot be blank")
	@Size(max = 20, message = "The RTN must be 20 characters.")
	private String rtn;

	@NotNull(message = "FAX cannot be null")
	@NotBlank(message = "FAX cannot be blank")
	private String fax;

	@NotNull(message = "PBX cannot be null")
	@NotBlank(message = "PBX cannot be blank")
	private String pbx;

	@NotNull(message = "EMAIL cannot be null")
	@NotBlank(message = "EMAIL cannot be blank")
	@Email(message = "The EMAIL is not a valid email.")
	private String email;

	@NotNull(message = "ACCTCODE cannot be null")
	@NotBlank(message = "ACCTCODE cannot be blank")
	private String acctCode;

	@NotNull(message = "FICTITIOUS_NUMBER cannot be null")
	@NotBlank(message = "FICTITIOUS_NUMBER cannot be blank")
	private String fictitiousNumber;

	@NotNull(message = "ID_COMPANY cannot be null")
	private Long idCompany;

	@NotNull(message = "ID_SYSTEM cannot be null")
	private Long idSystem;

	@NotNull(message = "WINERY_CODE cannot be null")
	@NotBlank(message = "WINERY_CODE cannot be blank")
	private String wineryCode;
	
	private String wineryName;

	@NotNull(message = "TERRITORY cannot be null")
	@NotBlank(message = "TERRITORY cannot be blank")
	private String territory;

	private Long status;

	private LocalDateTime created;
	private String wareHouseManager;
}
