package hn.com.tigo.comodatos.models;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDetailModel {

	private Long id;

	private Long idPrefecture;

	@NotNull(message = "MODEL cannot be null")
	@NotBlank(message = "MODEL cannot be blank")
	private String model;

	private String description;

	@NotNull(message = "QUANTITY cannot be null")
	@Positive(message = "QUANTITY must be a positive number")
	private Double quantity;

	@NotNull(message = "UNIT_PRICE cannot be null")
	@Positive(message = "UNIT_PRICE must be a positive number")
	private Double unitPrice;

	@NotNull(message = "AMOUNT_TOTAL cannot be null")
	@Positive(message = "AMOUNT_TOTAL must be a positive number")
	private Double amountTotal;

	private String serieOrBoxNumber;

	private Long status;

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

}
