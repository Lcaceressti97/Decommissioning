package hn.com.tigo.comodatos.models;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MooringModel {
	
	// Props
	private Long id;
	
	@NotNull(message = "idMooringBilling cannot be null")
	@Positive(message = "idMooringBilling must be a positive number")
	private Long idMooringBilling;
	
	private String customerAccount;
	
	private String billingAccount;
	
	private String subscriberId;
	
	private String userName;
	
	private LocalDateTime dateOfEntry;
	
	private String checkInTime;
	
	private String unmooringUser;
	
	private LocalDateTime unmooringDate;
	
	private int mooringStatus;
	
	private LocalDateTime created;

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


	
}
