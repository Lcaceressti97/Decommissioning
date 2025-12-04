package hn.com.tigo.equipmentinsurance.models;

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
public class UsersModel {

	private Long id;

	@NotNull(message = "NAME cannot be null")
	@NotBlank(message = "NAME cannot be blank")
	private String name;

	@NotNull(message = "USER_NAME cannot be null")
	@NotBlank(message = "USER_NAME cannot be blank")
	private String userName;

	@NotNull(message = "ID_USER cannot be null")
	@NotBlank(message = "ID_USER cannot be blank")
	@Email(message = "The EMAIL is not a valid email.")
	private String email;

	@NotNull(message = "TYPE USER cannot be null")
	private Long typeUser;

	private Long status;

	private LocalDateTime created;

}