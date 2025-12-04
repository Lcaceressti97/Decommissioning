package hn.com.tigo.equipmentaccessoriesbilling.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.equipmentaccessoriesbilling.models.PremiumRatesModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "SE_PREMIUM_RATES")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PremiumRatesEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "MODEL", length = 100)
	private String model;

	@Column(name = "DEVICE_VALUE", length = 100)
	private String deviceValue;

	@Column(name = "MONTHLY_PREMIUM", length = 100)
	private Double monthlyPremium;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "CREATED", nullable = false)
	private LocalDateTime created;

	public PremiumRatesModel entityToModel() {
		PremiumRatesModel model = new PremiumRatesModel();

		model.setId(this.getId());
		model.setModel(this.getModel());
		model.setDeviceValue(this.getDeviceValue());
		model.setMonthlyPremium(this.getMonthlyPremium());
		model.setStatus(this.getStatus());
		model.setCreated(this.getCreated());
		return model;
	}

}

