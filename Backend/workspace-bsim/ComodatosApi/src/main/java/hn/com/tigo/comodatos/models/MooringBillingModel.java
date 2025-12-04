package hn.com.tigo.comodatos.models;

import java.time.LocalDateTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MooringBillingModel {

	// Props
	private Long id;

	@NotNull(message = "cannot be null")
	@Positive(message = "must be a positive number")
	@Max(value = 99999999L, message = "maximum value allowed is up to 99999999")
	private Long correlativeCmd;
	
	@NotNull(message = "cannot be null")
	@Positive(message = "must be a positive number")
	@Max(value = 999L, message = "maximum value allowed is up to 999")
	private Long correlativeMooringCmd;
	
	@NotNull(message = "cannot be null")
	@Positive(message = "must be a positive number")
	@Pattern(regexp = "\\d+", message = "must be a number")
	private String subscriberId;

	@NotNull(message = "cannot be null")
	@Positive(message = "must be a positive number")
	private int monthsOfPermanence;
	
	private String cmdStatus;
	
	private String userName;
	
	private String supervisorUser;
	
	private String userMooring;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String userCancelled;
	
	private LocalDateTime dateOfAdmission;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private LocalDateTime dueDate;
	
	private Long invoiceLocation;
	
	private Long invoiceSubLocity;
	
	private String invoiceType;
	
	private String invoiceReading;
	
	private Long invoiceNumber;
	
	private String inventoryType;
	
	private String inventoryModel;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String teamSeries;
	
	private Double phoneCost;
	
	private Double phoneDiscount;
	
	@NotNull(message = "cannot be null")
	private Double vac;
	
	private Long mooring;
	
	private String promotion;
	
	private String transactionId;
	
	@NotNull(message = "cannot be null")
	private String customerAccount;
	
	private String serviceAccount;
	
	@NotNull(message = "cannot be null")
	private String billingAccount;
	
	private String observations;
	
	private LocalDateTime createDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCorrelativeCmd() {
		return correlativeCmd;
	}

	public void setCorrelativeCmd(Long correlativeCmd) {
		this.correlativeCmd = correlativeCmd;
	}

	public Long getCorrelativeMooringCmd() {
		return correlativeMooringCmd;
	}

	public void setCorrelativeMooringCmd(Long correlativeMooringCmd) {
		this.correlativeMooringCmd = correlativeMooringCmd;
	}

	public String getSubscriberId() {
		return subscriberId;
	}

	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}

	public int getMonthsOfPermanence() {
		return monthsOfPermanence;
	}

	public void setMonthsOfPermanence(int monthsOfPermanence) {
		this.monthsOfPermanence = monthsOfPermanence;
	}

	public String getCmdStatus() {
		return cmdStatus;
	}

	public void setCmdStatus(String cmdStatus) {
		this.cmdStatus = cmdStatus;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSupervisorUser() {
		return supervisorUser;
	}

	public void setSupervisorUser(String supervisorUser) {
		this.supervisorUser = supervisorUser;
	}

	public String getUserMooring() {
		return userMooring;
	}

	public void setUserMooring(String userMooring) {
		this.userMooring = userMooring;
	}

	public String getUserCancelled() {
		return userCancelled;
	}

	public void setUserCancelled(String userCancelled) {
		this.userCancelled = userCancelled;
	}

	public LocalDateTime getDateOfAdmission() {
		return dateOfAdmission;
	}

	public void setDateOfAdmission(LocalDateTime dateOfAdmission) {
		this.dateOfAdmission = dateOfAdmission;
	}

	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

	public Long getInvoiceLocation() {
		return invoiceLocation;
	}

	public void setInvoiceLocation(Long invoiceLocation) {
		this.invoiceLocation = invoiceLocation;
	}

	public Long getInvoiceSubLocity() {
		return invoiceSubLocity;
	}

	public void setInvoiceSubLocity(Long invoiceSubLocity) {
		this.invoiceSubLocity = invoiceSubLocity;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceReading() {
		return invoiceReading;
	}

	public void setInvoiceReading(String invoiceReading) {
		this.invoiceReading = invoiceReading;
	}

	public Long getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(Long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getInventoryType() {
		return inventoryType;
	}

	public void setInventoryType(String inventoryType) {
		this.inventoryType = inventoryType;
	}

	public String getInventoryModel() {
		return inventoryModel;
	}

	public void setInventoryModel(String inventoryModel) {
		this.inventoryModel = inventoryModel;
	}

	public String getTeamSeries() {
		return teamSeries;
	}

	public void setTeamSeries(String teamSeries) {
		this.teamSeries = teamSeries;
	}

	public Double getPhoneCost() {
		return phoneCost;
	}

	public void setPhoneCost(Double phoneCost) {
		this.phoneCost = phoneCost;
	}

	public Double getPhoneDiscount() {
		return phoneDiscount;
	}

	public void setPhoneDiscount(Double phoneDiscount) {
		this.phoneDiscount = phoneDiscount;
	}

	public Double getVac() {
		return vac;
	}

	public void setVac(Double vac) {
		this.vac = vac;
	}

	public Long getMooring() {
		return mooring;
	}

	public void setMooring(Long mooring) {
		this.mooring = mooring;
	}

	public String getPromotion() {
		return promotion;
	}

	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getCustomerAccount() {
		return customerAccount;
	}

	public void setCustomerAccount(String customerAccount) {
		this.customerAccount = customerAccount;
	}

	public String getServiceAccount() {
		return serviceAccount;
	}

	public void setServiceAccount(String serviceAccount) {
		this.serviceAccount = serviceAccount;
	}

	public String getBillingAccount() {
		return billingAccount;
	}

	public void setBillingAccount(String billingAccount) {
		this.billingAccount = billingAccount;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}
	
}
