package hn.com.tigo.equipmentaccessoriesbilling.models;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsuranceClaimModel {

	private Long id;

	@NotNull
	@Size(min = 1, max = 30, message = "size must be between 1 and 30")
	private String customerAccount;

	private String customerName;

	@NotNull
	@Size(min = 1, max = 30, message = "size must be between 1 and 30")
	private String serviceAccount;

	@NotNull
	@Size(min = 1, max = 30, message = "size must be between 1 and 30")
	private String billingAccount;

	@NotNull
	@Size(min = 1, max = 12, message = "size must be between 1 and 12")
	private String phone;

	private String phoneStatus;

	// @Size(min = 1, max = 3, message = "size must be between 1 and 3")
	private String clientType;

	private Long actualPrice;

	private String actualEsn;

	private String actualModel;

	private String actualInvType;

	private String reasonClaim;

	private String newEsn;

	private String newModel;

	private String newModelDescription;

	private String newInvType;

	private String userCreate;

	private LocalDateTime dateCreate;

	private String userResolution;

	private LocalDateTime dateResolution;

	private String invoiceType;

	private String invoiceLetter;

	private Long invoiceNumber;

	private Long branchAnnex;

	private String statusClaim;

	private String observations;

	private Double insurancePremium;

	private Double deductible;

	private String warehouse;

	private String bandit;

	private String workshopOrderNumber;

	private Double priceAdjustment;

	private Double adjustmentPremiums;

}