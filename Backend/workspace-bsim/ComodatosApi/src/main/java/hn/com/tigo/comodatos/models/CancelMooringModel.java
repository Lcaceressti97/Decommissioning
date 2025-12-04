package hn.com.tigo.comodatos.models;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelMooringModel {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String subscriber;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String billingAccount;
	
	@NotNull(message = "cannot be null")
	private String type;
	
	@NotNull(message = "cannot be null")
	private String description;
	
	@NotNull(message = "cannot be null")
	private String cancellationUser;
	
	private LocalDateTime dueDate;

	public String getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(String subscriber) {
		this.subscriber = subscriber;
	}

	public String getBillingAccount() {
		return billingAccount;
	}

	public void setBillingAccount(String billingAccount) {
		this.billingAccount = billingAccount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCancellationUser() {
		return cancellationUser;
	}

	public void setCancellationUser(String cancellationUser) {
		this.cancellationUser = cancellationUser;
	}

	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}
	
}
