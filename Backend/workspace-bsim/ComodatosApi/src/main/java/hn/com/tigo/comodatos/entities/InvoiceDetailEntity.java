package hn.com.tigo.comodatos.entities;

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

import hn.com.tigo.comodatos.models.InvoiceDetailModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "MEA_INVOICE_DETAIL")
@Entity
//@Data
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BillingEntity getBilling() {
		return billing;
	}

	public void setBilling(BillingEntity billing) {
		this.billing = billing;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getAmountTotal() {
		return amountTotal;
	}

	public void setAmountTotal(Double amountTotal) {
		this.amountTotal = amountTotal;
	}

	public String getSerieOrBoxNumber() {
		return serieOrBoxNumber;
	}

	public void setSerieOrBoxNumber(String serieOrBoxNumber) {
		this.serieOrBoxNumber = serieOrBoxNumber;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
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
