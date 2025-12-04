package hn.com.tigo.jteller.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Table(name = "MEA_INVOICE_DETAIL")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@NotNull(message = "ID_PREFECTURE cannot be null")
	@Column(name = "ID_PREFECTURE")
	private Long idPrefecture;

	@NotBlank(message = "MODEL cannot be blank")
	@Column(name = "MODEL", length = 150)
	private String model;

	@NotBlank(message = "DESCRIPTION cannot be blank")
	@Column(name = "DESCRIPTION", length = 100)
	private String description;

	@NotNull(message = "AMOUNT cannot be null")
	@Positive(message = "AMOUNT must be a positive number")
	@Column(name = "AMOUNT")
	private Double amount;

	@NotNull(message = "UNIT_PRICE cannot be null")
	@Positive(message = "UNIT_PRICE must be a positive number")
	@Column(name = "UNIT_PRICE")
	private Double unitPrice;

	@NotNull(message = "TOTAL cannot be null")
	@Positive(message = "TOTAL must be a positive number")
	@Column(name = "TOTAL")
	private Double total;

	@NotBlank(message = "SERIE_OR_BOX_NUMBER cannot be blank")
	@Column(name = "SERIE_OR_BOX_NUMBER", length = 50)
	private String serieOrBoxNumber;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "CREATED", nullable = false)
	private LocalDateTime created;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdPrefecture() {
		return idPrefecture;
	}

	public void setIdPrefecture(Long idPrefecture) {
		this.idPrefecture = idPrefecture;
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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
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


	
}
