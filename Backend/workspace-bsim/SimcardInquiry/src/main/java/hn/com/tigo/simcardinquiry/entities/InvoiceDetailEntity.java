package hn.com.tigo.simcardinquiry.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import hn.com.tigo.simcardinquiry.models.InvoiceDetailModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "MEA_INVOICE_DETAIL")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InvoiceDetailEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_MEA_INVOICE_DETAIL")
	@SequenceGenerator(name = "SQ_MEA_INVOICE_DETAIL", sequenceName = "SQ_MEA_INVOICE_DETAIL", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PREFECTURE", nullable = false)
	@JsonIgnore
	private BillingEntity billing;

	@Column(name = "MODEL", length = 150)
	private String model;

	@Column(name = "DESCRIPTION", length = 100)
	private String description;

	@Column(name = "QUANTITY")
	private Double quantity;

	@Column(name = "UNIT_PRICE")
	private Double unitPrice;

	@Column(name = "AMOUNT_TOTAL")
	private Double amountTotal;

	@Column(name = "SERIE_OR_BOX_NUMBER", length = 50)
	private String serieOrBoxNumber;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "CREATED", nullable = false)
	private LocalDateTime created;

	public InvoiceDetailModel entityToModel() {
		InvoiceDetailModel model = new InvoiceDetailModel();
		model.setId(this.getId());
		model.setModel(this.getModel());
		model.setDescription(this.getDescription());
		model.setQuantity(this.getQuantity());
		model.setUnitPrice(this.getUnitPrice());
		model.setAmountTotal(this.getAmountTotal());
		model.setSerieOrBoxNumber(this.getSerieOrBoxNumber());
		model.setStatus(this.getStatus());
		model.setCreated(this.getCreated());
		return model;
	}
}
