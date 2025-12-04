package hn.com.tigo.equipmentinsurance.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.equipmentinsurance.models.SafeControlEquipmentModel;

import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "SE_SAFE_CONTROL_EQUIPMENT")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SafeControlEquipmentEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "SUBSCRIBER_ID", length = 30)
	private String subscriberId;

	@Column(name = "ESN", length = 15)
	private String esn;

	@Column(name = "CUSTOMER_ACCOUNT", length = 30)
	private String customerAccount;

	@Column(name = "SERVICE_ACCOUNT", length = 30)
	private String serviceAccount;

	@Column(name = "BILLING_ACCOUNT", length = 30)
	private String billingAccount;

	@Column(name = "PHONE", length = 12)
	private String phone;

	@Column(name = "PHONE_MODEL", length = 6)
	private String phoneModel;

	@Column(name = "ORIGIN_PHONE", length = 1)
	private String originPhone;

	@Column(name = "INVENTORY_TYPE", length = 1)
	private String inventoryType;

	@Column(name = "ORIGIN_TYPE", length = 1)
	private String originType;

	@Column(name = "DATE_START_SERVICE", length = 50)
	private LocalDateTime dateStartService;

	@Column(name = "DATE_INIT")
	private LocalDateTime dateInit;

	@Column(name = "DATE_END")
	private LocalDateTime dateEnd;

	@Column(name = "DATE_INCLUSION")
	private LocalDateTime dateInclusion;

	@Column(name = "MONTHLY_FEE", length = 9)
	private Long monthlyFee;

	@Column(name = "CURRENT_PERIOD", length = 3)
	private Long currentPeriod;

	@Column(name = "INSURANCE_STATUS", length = 1)
	private Long insuranceStatus;

	@Column(name = "USER_AS400", length = 10)
	private String userAs400;

	@Column(name = "DATE_TRANSACTION")
	private LocalDateTime dateTransaction;

	@Column(name = "OPERATION_PROGRAM", length = 10)
	private String operationProgram;

	@Column(name = "PERIOD_2", length = 3)
	private Long period2;

	@Column(name = "MONTHLY_FEE_2", length = 9)
	private Long monthlyFee2;

	@Column(name = "PERIOD_3", length = 3)
	private Long period3;

	@Column(name = "MONTHLY_FEE_3", length = 9)
	private Long monthlyFee3;

	public SafeControlEquipmentModel entityToModel() {
		SafeControlEquipmentModel model = new SafeControlEquipmentModel();
		model.setId(this.getId());
		model.setSubscriberId(this.getSubscriberId());
		model.setEsn(this.getEsn());
		model.setCustomerAccount(this.getCustomerAccount());
		model.setServiceAccount(this.getServiceAccount());
		model.setBillingAccount(this.getBillingAccount());
		model.setPhone(this.getPhone());
		model.setPhoneModel(this.getPhoneModel());
		model.setOriginPhone(this.getOriginPhone());
		model.setInventoryType(this.getInventoryType());
		model.setOriginType(this.getOriginType());
		model.setDateStartService(this.getDateStartService());
		model.setDateInit(this.getDateInit());
		model.setDateEnd(this.getDateEnd());
		model.setDateInclusion(this.getDateInclusion());
		model.setMonthlyFee(this.getMonthlyFee());
		model.setCurrentPeriod(this.getCurrentPeriod());
		model.setInsuranceStatus(this.getInsuranceStatus());
		model.setUserAs400(this.getUserAs400());
		model.setDateTransaction(this.getDateTransaction());
		model.setOperationProgram(this.getOperationProgram());
		model.setPeriod2(this.getPeriod2());
		model.setMonthlyFee2(this.getMonthlyFee2());
		model.setPeriod3(this.getPeriod3());
		model.setMonthlyFee3(this.getMonthlyFee3());

		return model;
	}

}
