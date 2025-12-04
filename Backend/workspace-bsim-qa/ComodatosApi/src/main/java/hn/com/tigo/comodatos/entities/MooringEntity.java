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

import hn.com.tigo.comodatos.models.MooringModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "CMD_MOORING")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MooringEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// Props
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CMD_MOORING_SEQ")
	@SequenceGenerator(name = "CMD_MOORING_SEQ", sequenceName = "CMD_MOORING_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "ID_MOORING_BILLING", nullable = false)
	private Long idMooringBilling;
	
	@Column(name = "CUSTOMER_ACCOUNT", length = 30)
	private String customerAccount;
	
	@Column(name = "BILLING_ACCOUNT", length = 30)
	private String billingAccount;
	
	@Column(name = "SUBSCRIBER_ID", length = 30)
	private String subscriberId;
	
	@Column(name = "USER_NAME", length = 50)
	private String userName;
	
	@Column(name = "DATE_OF_ENTRY")
	private LocalDateTime dateOfEntry;
	
	@Column(name = "CHECK_IN_TIME", length = 10)
	private String checkInTime;
	
	@Column(name = "UNMOORING_USER", length = 30)
	private String unmooringUser;
	
	@Column(name = "UNMOORING_DATE")
	private LocalDateTime unmooringDate;
	
	@Column(name = "MOORING_STATUS")
	private int mooringStatus;
	
	@Column(name = "CREATED")
	private LocalDateTime created;
	
	public MooringModel entityToModel() {
		MooringModel model = new MooringModel();
		
		model.setId(this.getId());
		model.setIdMooringBilling(this.getIdMooringBilling());
		model.setCustomerAccount(this.getCustomerAccount());
		model.setBillingAccount(this.getBillingAccount());
		model.setSubscriberId(this.getSubscriberId());
		model.setUserName(this.getUserName());
		model.setDateOfEntry(this.getDateOfEntry());
		model.setCheckInTime(this.getCheckInTime());
		model.setUnmooringUser(this.getUnmooringUser());
		model.setUnmooringDate(this.getUnmooringDate());
		model.setMooringStatus(this.getMooringStatus());
		model.setCreated(this.getCreated());
		
		return model;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdMooringBilling() {
		return idMooringBilling;
	}

	public void setIdMooringBilling(Long idMooringBilling) {
		this.idMooringBilling = idMooringBilling;
	}

	public String getCustomerAccount() {
		return customerAccount;
	}

	public void setCustomerAccount(String customerAccount) {
		this.customerAccount = customerAccount;
	}

	public String getBillingAccount() {
		return billingAccount;
	}

	public void setBillingAccount(String billingAccount) {
		this.billingAccount = billingAccount;
	}

	public String getSubscriberId() {
		return subscriberId;
	}

	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public LocalDateTime getDateOfEntry() {
		return dateOfEntry;
	}

	public void setDateOfEntry(LocalDateTime dateOfEntry) {
		this.dateOfEntry = dateOfEntry;
	}

	public String getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}

	public String getUnmooringUser() {
		return unmooringUser;
	}

	public void setUnmooringUser(String unmooringUser) {
		this.unmooringUser = unmooringUser;
	}

	public LocalDateTime getUnmooringDate() {
		return unmooringDate;
	}

	public void setUnmooringDate(LocalDateTime unmooringDate) {
		this.unmooringDate = unmooringDate;
	}

	public int getMooringStatus() {
		return mooringStatus;
	}

	public void setMooringStatus(int mooringStatus) {
		this.mooringStatus = mooringStatus;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
