package hn.com.tigo.comodatos.entities;

import java.io.Serializable;

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

import hn.com.tigo.comodatos.models.PromotionsDetailModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "CMD_PROMOTIONS_DETAILS")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionsDetailEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PROMOTIONS_DETAILS")
	@SequenceGenerator(name = "SQ_PROMOTIONS_DETAILS", sequenceName = "SQ_PROMOTIONS_DETAILS", allocationSize = 1)
	@Column(name = "ID")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PROMOTION", nullable = false)
	@JsonIgnore
	private PromotionsEntity detail;

	@Column(name = "PROMOTION_CODE", length = 50)
	private String promotionCode;

	@Column(name = "MODEL_CODE", length = 50)
	private String modelCode;

	@Column(name = "PLAN_VALUE")
	private Double planValue;

	@Column(name = "MONTHS_PERMANENCE")
	private Double monthsPermanence;

	@Column(name = "SUBSIDY_FUND")
	private Double subsidyFund;

	@Column(name = "ADDITIONAL_SUBSIDY")
	private Double additionalSubsidy;

	@Column(name = "INSTITUTIONAL_FUNDS")
	private Double institutionalFunds;

	@Column(name = "COOPS_FUND")
	private Double coopsFund;
	
	@Column(name = "STATUS")
	private Long status;

	public PromotionsDetailModel entityToModel() {
		PromotionsDetailModel model = new PromotionsDetailModel();
		model.setId(this.getId());
		model.setPromotionCode(this.getPromotionCode());
		model.setModelCode(this.getModelCode());
		model.setPlanValue(this.getPlanValue());
		model.setMonthsPermanence(this.getMonthsPermanence());
		model.setSubsidyFund(this.getSubsidyFund());
		model.setAdditionalSubsidy(this.getAdditionalSubsidy());
		model.setInstitutionalFunds(this.getInstitutionalFunds());
		model.setCoopsFund(this.getCoopsFund());
		model.setStatus(this.getStatus());
		return model;
	}

//	public PromotionsDetailEntity(Long id, PromotionsEntity detail, String promotionCode, String modelCode,
//			Double planValue, Double monthsPermanence, Double subsidyFund, Double additionalSubsidy,
//			Double institutionalFunds, Double coopsFund, Long status) {
//		super();
//		this.id = id;
//		this.detail = detail;
//		this.promotionCode = promotionCode;
//		this.modelCode = modelCode;
//		this.planValue = planValue;
//		this.monthsPermanence = monthsPermanence;
//		this.subsidyFund = subsidyFund;
//		this.additionalSubsidy = additionalSubsidy;
//		this.institutionalFunds = institutionalFunds;
//		this.coopsFund = coopsFund;
//		this.status = status;
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PromotionsEntity getDetail() {
		return detail;
	}

	public void setDetail(PromotionsEntity detail) {
		this.detail = detail;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
