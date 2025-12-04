package hn.com.tigo.equipmentaccessoriesbilling.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.equipmentaccessoriesbilling.models.InsuranceRatesModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "SE_INSURANCE_RATES_MODEL")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceRatesEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;
	
	@Column(name = "EFFECTIVE_DATE")
	private LocalDateTime efectiveDate;
	
	@Column(name = "PERIOD_NUMBER", length = 1)
	private Long periodNumber;
	
	@Column(name = "VALUE_FROM")
	private Double valueFrom;

	@Column(name = "VALUE_UP")
	private Double valueUp;
	
	@Column(name = "MONTHLY_FEE")
	private Double monthlyFee;
	
	@Column(name = "TEXT_COVERAGE", length = 80)
	private String textCoverage;
	
	@Column(name = "MODEL", length = 6)
	private String model;
	
	public InsuranceRatesModel entityToModel() {
		InsuranceRatesModel model = new InsuranceRatesModel();
		model.setId(this.getId());
		model.setEfectiveDate(this.getEfectiveDate());
		model.setPeriodNumber(this.getPeriodNumber());
		model.setValueFrom(this.getValueFrom());
		model.setValueUp(this.getValueUp());
		model.setMonthlyFee(this.getMonthlyFee());
		model.setTextCoverage(this.getTextCoverage());
		model.setModel(this.getModel());
		return model;
	}
}