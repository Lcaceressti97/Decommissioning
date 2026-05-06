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

}
