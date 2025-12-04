package hn.com.tigo.comodatos.models;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import hn.com.tigo.comodatos.entities.PromotionsDetailEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionsModel {

	private Long id;

	@NotNull(message = "Promotion Code is required")
	private String promotionCode;

	@NotNull(message = "Model Code is required")
	private String modelCode;

	private String description;

	private String corporate;

	private String permanentValidity;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

	private List<PromotionsDetailEntity> promotionsDetail;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPromotionCode() {
		return promotionCode;
	}

	public void setPromotionCode(String promotionCode) {
		this.promotionCode = promotionCode;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCorporate() {
		return corporate;
	}

	public void setCorporate(String corporate) {
		this.corporate = corporate;
	}

	public String getPermanentValidity() {
		return permanentValidity;
	}

	public void setPermanentValidity(String permanentValidity) {
		this.permanentValidity = permanentValidity;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public List<PromotionsDetailEntity> getPromotionsDetail() {
		return promotionsDetail;
	}

	public void setPromotionsDetail(List<PromotionsDetailEntity> promotionsDetail) {
		this.promotionsDetail = promotionsDetail;
	}
	
}
