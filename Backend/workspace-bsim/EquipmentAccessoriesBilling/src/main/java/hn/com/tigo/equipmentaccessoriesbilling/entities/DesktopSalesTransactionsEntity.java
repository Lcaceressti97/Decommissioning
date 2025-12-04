package hn.com.tigo.equipmentaccessoriesbilling.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.equipmentaccessoriesbilling.models.DesktopSalesTransactionsModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "MEA_DESKTOP_SALES_TRANSACTIONS")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DesktopSalesTransactionsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "LOCATION")
	private Long location;

	@Column(name = "PERIOD_DATE", nullable = false)
	private LocalDateTime periodDate;

	@Column(name = "AGENCY")
	private Long agency;

	@Column(name = "ANNEXED")
	private Long annexed;

	@Column(name = "SELLER")
	private Long seller;

	@Column(name = "CHARGE_AMOUNT")
	private Double chargeAmount;

	
	@Column(name = "PAYMENT_AMOUNT")
	private Double paymentAmount;

	@Column(name = "CLOSING_DATE", nullable = false)
	private LocalDateTime closingDate;


	@Column(name = "STS", length = 1)
	private String sts;

	public DesktopSalesTransactionsModel entityToModel() {
		DesktopSalesTransactionsModel model = new DesktopSalesTransactionsModel();
		model.setId(this.getId());
		model.setLocation(this.getLocation());
		model.setPeriodDate(this.getPeriodDate());
		model.setAgency(this.getAgency());
		model.setAnnexed(this.getAnnexed());
		model.setSeller(this.getSeller());
		model.setChargeAmount(this.getChargeAmount());
		model.setPaymentAmount(this.getPaymentAmount());
		model.setClosingDate(this.getClosingDate());
		model.setSts(this.getSts());

		return model;
	}
}
