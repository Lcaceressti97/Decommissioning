package hn.com.tigo.comodatos.models;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionsDetailModel {

	private Long id;

	@NotNull(message = "Promotion Code is required")
	private String promotionCode;

	@NotNull(message = "Model Code is required")
	private String modelCode;

	private Double planValue;

	private Double monthsPermanence;

	private Double subsidyFund;

	private Double additionalSubsidy;

	private Double institutionalFunds;

	private Double coopsFund;
	
	private Long status;

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

	public Double getPlanValue() {
		return planValue;
	}

	public void setPlanValue(Double planValue) {
		this.planValue = planValue;
	}

	public Double getMonthsPermanence() {
		return monthsPermanence;
	}

	public void setMonthsPermanence(Double monthsPermanence) {
		this.monthsPermanence = monthsPermanence;
	}

	public Double getSubsidyFund() {
		return subsidyFund;
	}

	public void setSubsidyFund(Double subsidyFund) {
		this.subsidyFund = subsidyFund;
	}

	public Double getAdditionalSubsidy() {
		return additionalSubsidy;
	}

	public void setAdditionalSubsidy(Double additionalSubsidy) {
		this.additionalSubsidy = additionalSubsidy;
	}

	public Double getInstitutionalFunds() {
		return institutionalFunds;
	}

	public void setInstitutionalFunds(Double institutionalFunds) {
		this.institutionalFunds = institutionalFunds;
	}

	public Double getCoopsFund() {
		return coopsFund;
	}

	public void setCoopsFund(Double coopsFund) {
		this.coopsFund = coopsFund;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

}
