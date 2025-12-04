package hn.com.tigo.inquiriesamsysnavega.models;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NavegaBankModel {

	private Long id;

	@NotNull(message = "Code cannot be null")
	@NotBlank(message = "The Code is required.")
	private String code;

	@NotNull(message = "Name Company cannot be null")
	@NotBlank(message = "The Name Company is required.")
	private String nameCompany;

	@NotNull(message = "Bank Name cannot be null")
	@NotBlank(message = "The Bank Name is required.")
	private String bankName;

	@NotNull(message = "Name Transaction cannot be null")
	@NotBlank(message = "The Name Transaction is required.")
	private String nameTransaction;

	@NotNull(message = "Bank Account Name cannot be null")
	@NotBlank(message = "The Bank Account Name is required.")
	private String bankAccountName;

	@NotNull(message = "Bank Account Num cannot be null")
	@NotBlank(message = "The Bank Account Num is required.")
	private String bankAccountNum;

	private String currencyCode;

	@NotNull(message = "Identifying Letter cannot be null")
	@NotBlank(message = "The Identifying Letter is required.")
	private String identifyingLetter;

	private String bankAccountEbs;

	private String bankCodeAmsys;

	private String company;

	private Long status;

	private LocalDateTime createdDate;

}
