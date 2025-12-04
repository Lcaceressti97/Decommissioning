package hn.com.tigo.equipmentaccessoriesbilling.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import hn.com.tigo.equipmentaccessoriesbilling.models.MooringBillingModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "CMD_MOORING_BILLING")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MooringBillingEntity implements Serializable {

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

	@Column(name = "CMD_STATUS", length = 1)
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

	@Column(name = "INVOICE_NUMBER")
	private String invoiceNumber;

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
}