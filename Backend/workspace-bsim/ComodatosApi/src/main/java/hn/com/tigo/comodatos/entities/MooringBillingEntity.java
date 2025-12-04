package hn.com.tigo.comodatos.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
//import javax.validation.constraints.DecimalMin;
//import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import hn.com.tigo.comodatos.models.MooringBillingModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "CMD_MOORING_BILLING")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MooringBillingEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	// Props
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CMD_MOORING_BILLING_SEQ")
	@SequenceGenerator(name = "CMD_MOORING_BILLING_SEQ", sequenceName = "CMD_MOORING_BILLING_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "CORRELATIVE_CMD", nullable = false)
	private Long correlativeCmd;
	
	@Column(name = "CORRELATIVE_MOORING_CMD", nullable = false)
	private Long correlativeMooringCmd;
	
	@Column(name = "SUBSCRIBER_ID", length = 30)
	private String subscriberId;

	@Column(name = "MONTHS_OF_PERMANENCE")
	private int monthsOfPermanence;
	
	@Column(name = "CMD_STATUS", length=1)
	private String cmdStatus;
	
	@Column(name = "USER_NAME", length = 50)
	private String userName;
	
	@Column(name = "SUPERVISOR_USER", length = 50)
	private String supervisorUser;
	
	@Column(name = "USER_MOORING", length = 50)
	private String userMooring;
	
	@Column(name = "USER_CANCELLED", length = 50)
	private String userCancelled;
	
	@Column(name = "DATE_OF_ADMISSION")
	private LocalDateTime dateOfAdmission;
	
	@Column(name = "DUE_DATE")
	private LocalDateTime dueDate;
	
	@Column(name = "INVOICE_LOCATION")
	private Long invoiceLocation;
	
	@Column(name = "INVOICE_SUBLOCITY")
	private Long invoiceSubLocity;
	
	@Column(name = "INVOICE_TYPE", length = 3)
	private String invoiceType;
	
	@Column(name = "INVOICE_READING", length = 1)
	private String invoiceReading;
	
	@NotNull(message = "INVOICE_NUMBER cannot be null")
	@Column(name = "INVOICE_NUMBER")
	private Long invoiceNumber;
	
	@Column(name = "INVENTORY_TYPE", length = 1)
	private String inventoryType;
	
	@Column(name = "INVENTORY_MODEL", length = 6)
	private String inventoryModel;
	
	@Column(name = "TEAM_SERIES", length = 25)
	private String teamSeries;
	
	@Column(name = "PHONE_COST")
	private Double phoneCost;
	
	@Column(name = "PHONE_DISCOUNT")
	private Double phoneDiscount;
	
	@Column(name = "VAC")
	private Double vac;
	
	@Column(name = "MOORING")
	private Long mooring;
	
	@Column(name = "PROMOTION", length = 15)
	private String promotion;
	
	@Column(name = "TRANSACTION_ID", length = 30)
	private String transactionId;
	
	@Column(name = "CUSTOMER_ACCOUNT", length = 30)
	private String customerAccount;
	
	@Column(name = "SERVICE_ACCOUNT", length = 30)
	private String serviceAccount;
	
	@Column(name = "BILLING_ACCOUNT", length = 30)
	private String billingAccount;
	
	@Column(name = "OBSERVATIONS", length = 500)
	private String observations;
	
	@Column(name = "CREATE_DATE")
	private LocalDateTime createDate;
	
	public MooringBillingModel entityToModel() {
		MooringBillingModel model = new MooringBillingModel();
		
		model.setId(this.getId());
		model.setCorrelativeCmd(this.getCorrelativeCmd());
		model.setCorrelativeMooringCmd(this.getCorrelativeMooringCmd());
		model.setSubscriberId(this.getSubscriberId());
		model.setMonthsOfPermanence(this.getMonthsOfPermanence());
		model.setCmdStatus(this.getCmdStatus());
		model.setUserName(this.getUserName());
		model.setSupervisorUser(this.getSupervisorUser());
		model.setUserMooring(this.getUserMooring());
		model.setUserCancelled(this.getUserCancelled());
		model.setDateOfAdmission(this.getDateOfAdmission());
		model.setDueDate(this.getDueDate());
		model.setInvoiceLocation(this.getInvoiceLocation());
		model.setInvoiceSubLocity(this.getInvoiceSubLocity());
		model.setInvoiceType(this.getInvoiceType());
		model.setInvoiceReading(this.getInvoiceReading());
		model.setInvoiceNumber(this.getInvoiceNumber());
		model.setInventoryType(this.getInventoryType());
		model.setInventoryModel(this.getInventoryModel());		
		model.setTeamSeries(this.getTeamSeries());		
		model.setPhoneCost(this.getPhoneCost());
		model.setPhoneDiscount(this.getPhoneDiscount());
		model.setVac(this.getVac());		
		model.setMooring(this.getMooring());
		model.setPromotion(this.getPromotion());
		model.setTransactionId(this.getTransactionId());
		model.setCustomerAccount(this.getCustomerAccount());
		model.setServiceAccount(this.getServiceAccount());
		model.setBillingAccount(this.getBillingAccount());
		model.setObservations(this.getObservations());
		model.setCreateDate(this.getCreateDate());
		
		return model;
	}

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
