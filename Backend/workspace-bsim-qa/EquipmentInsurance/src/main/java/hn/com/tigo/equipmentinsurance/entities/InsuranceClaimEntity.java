package hn.com.tigo.equipmentinsurance.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.equipmentinsurance.models.InsuranceClaimModel;

import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "SE_INSURANCE_CLAIM")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceClaimEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "CUSTOMER_ACCOUNT", length = 30)
	private String customerAccount;
	
	@Column(name = "CUSTOMER_NAME", length = 250)
	private String customerName;

	@Column(name = "SERVICE_ACCOUNT", length = 30)
	private String serviceAccount;

	@Column(name = "BILLING_ACCOUNT", length = 30)
	private String billingAccount;

	@Column(name = "PHONE", length = 12)
	private String phone;

	@Column(name = "PHONE_STATUS", length = 1)
	private String phoneStatus;

	@Column(name = "CLIENT_TYPE", length = 3)
	private String clientType;

	@Column(name = "ACTUAL_PRICE", length = 13)
	private Double actualPrice;

	@Column(name = "ACTUAL_ESN", length = 15)
	private String actualEsn;

	@Column(name = "ACTUAL_MODEL", length = 6)
	private String actualModel;

	@Column(name = "ACTUAL_INV_TYPE", length = 1)
	private String actualInvType;

	@Column(name = "REASON_CLAIM", length = 20)
	private String reasonClaim;

	@Column(name = "NEW_ESN", length = 15)
	private String newEsn;

	@Column(name = "NEW_MODEL", length = 6)
	private String newModel;
	
	@Column(name = "NEW_MODEL_DESCRIPTION", length = 6)
	private String newModelDescription;

	@Column(name = "NEW_INV_TYPE", length = 1)
	private String newInvType;

	@Column(name = "USER_CREATE", length = 50)
	private String userCreate;

	@Column(name = "DATE_CREATE")
	private LocalDateTime dateCreate;

	@Column(name = "USER_RESOLUTION", length = 10)
	private String userResolution;

	@Column(name = "DATE_RESOLUTION")
	private LocalDateTime dateResolution;

	@Column(name = "INVOICE_TYPE", length = 3)
	private String invoiceType;

	@Column(name = "INVOICE_LETTER", length = 1)
	private String invoiceLetter;

	@Column(name = "INVOICE_NUMBER", length = 8)
	private Long invoiceNumber;

	@Column(name = "BRANCH_ANNEX", length = 10)
	private Long branchAnnex;

	@Column(name = "STATUS_CLAIM", length = 1)
	private String statusClaim;

	@Column(name = "OBSERVATIONS", length = 300)
	private String observations;

	@Column(name = "INSURANCE_PREMIUM")
	private Double insurancePremium;
	
	@Column(name = "DEDUCTIBLE")
	private Double deductible;
	
	@Column(name = "WAREHOUSE", length = 100)
	private String warehouse;
	
	@Column(name = "BANDIT", length = 50)
	private String bandit;
	
	@Column(name = "WORKSHOP_ORDER_NUMBER", length = 100)
	private String workshopOrderNumber;

	@Column(name = "PRICE_ADJUSTMENT")
	private Double priceAdjustment;
	
	@Column(name = "ADJUSTMENT_PREMIUMS")
	private Double adjustmentPremiums;

	@Column(name = "INSURED_SUM", length = 13)
	private Double insuredSum;
	
	public InsuranceClaimModel entityToModel() {
		InsuranceClaimModel model = new InsuranceClaimModel();
		model.setId(this.getId());
		model.setCustomerAccount(this.getCustomerAccount());
		model.setServiceAccount(this.getServiceAccount());
		model.setCustomerName(this.getCustomerName());
		model.setBillingAccount(this.getBillingAccount());
		model.setPhone(this.getPhone());
		model.setPhoneStatus(this.getPhoneStatus());
		model.setClientType(this.getClientType());
		model.setActualPrice(this.getActualPrice());
		model.setActualEsn(this.getActualEsn());
		model.setActualModel(this.getActualModel());
		model.setActualInvType(this.getActualInvType());
		model.setReasonClaim(this.getReasonClaim());
		model.setNewEsn(this.getNewEsn());
		model.setNewModel(this.getNewModel());
		model.setNewInvType(this.getNewInvType());
		model.setUserCreate(this.getUserCreate());
		model.setDateCreate(this.getDateCreate());
		model.setUserResolution(this.getUserResolution());
		model.setDateResolution(this.getDateResolution());
		model.setInvoiceType(this.getInvoiceType());
		model.setInvoiceLetter(this.getInvoiceLetter());
		model.setInvoiceNumber(this.getInvoiceNumber());
		model.setBranchAnnex(this.getBranchAnnex());
		model.setStatusClaim(this.getStatusClaim());
		model.setObservations(this.getObservations());
		model.setInsurancePremium(this.getInsurancePremium());
		model.setDeductible(this.getDeductible());
		model.setWarehouse(this.getWarehouse());
		model.setBandit(this.getBandit());
		model.setNewModelDescription(this.getNewModelDescription());
		model.setWorkshopOrderNumber(this.getWorkshopOrderNumber());
		model.setPriceAdjustment(this.getPriceAdjustment());
		model.setAdjustmentPremiums(this.getAdjustmentPremiums());
		model.setInsuredSum(this.getInsuredSum());
		return model;
	}
}
