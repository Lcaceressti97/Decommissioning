package hn.com.tigo.simcardinquiry.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.simcardinquiry.models.SimcardDetailModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "SIMCARD_DETAIL")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimcardDetailEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "ID_ORDER_CONTROL")
	private Long idOrderControl;

	@Column(name = "ICC")
	private String icc;

	@Column(name = "IMSI")
	private String imsi;

	@Column(name = "IMSIB")
	private String imsib;

	@Column(name = "KI")
	private String ki;

	@Column(name = "PIN1")
	private String pin1;

	@Column(name = "PUK1")
	private String puk1;

	@Column(name = "PIN2")
	private String pin2;

	@Column(name = "PUK2")
	private String puk2;

	@Column(name = "ADM1")
	private String adm1;

	@Column(name = "ADM2")
	private String adm2;

	@Column(name = "ADM3")
	private String adm3;

	@Column(name = "ACC")
	private String acc;

	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "ORDER_EBS")
	private String orderEbs;
	
	@Column(name = "CUSTOMER_EBS")
	private String customerEbs;
	
	@Column(name = "BILLING_STATUS")
	private String billingStatus;
	
	@Column(name = "MODEL")
	private String model;
	
	@Column(name = "WAREHOUSE")
	private String warehouse;

	public SimcardDetailModel entityToModel() {
		SimcardDetailModel model = new SimcardDetailModel();
		model.setId(this.getId());
		model.setIdOrderControl(this.getIdOrderControl());
		model.setIcc(this.getIcc());
		model.setImsi(this.getImsi());
		model.setImsib(this.getImsib());
		model.setKi(this.getKi());
		model.setPin1(this.getPin1());
		model.setPuk1(this.getPuk1());
		model.setPin2(this.getPin2());
		model.setPuk2(this.getPuk2());
		model.setAdm1(this.getAdm1());
		model.setAdm2(this.getAdm2());
		model.setAdm3(this.getAdm3());
		model.setAcc(this.getAcc());
		model.setStatus(this.getStatus());
		model.setOrderEbs(this.getOrderEbs());
		model.setCustomerEbs(this.getCustomerEbs());
		model.setBillingStatus(this.getBillingStatus());
		model.setModel(this.getModel());
		model.setWarehouse(this.getWarehouse());
		return model;
	}

}
