package hn.com.tigo.comodatos.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import hn.com.tigo.comodatos.models.PromotionsModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "CMD_PROMOTIONS")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CMD_PROMOTIONS")
	@SequenceGenerator(name = "SQ_CMD_PROMOTIONS", sequenceName = "SQ_CMD_PROMOTIONS", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@Column(name = "PROMOTION_CODE", length = 50)
	private String promotionCode;

	@Column(name = "MODEL_CODE", length = 50)
	private String modelCode;

	@Column(name = "DESCRIPTION", length = 60)
	private String description;

	@Column(name = "CORPORATE", length = 50)
	private String corporate;

	@Column(name = "PERMANENT_VALIDITY", length = 50)
	private String permanentValidity;

	@Column(name = "START_DATE")
	private LocalDateTime startDate;

	@Column(name = "END_DATE")
	private LocalDateTime endDate;

	@OneToMany(mappedBy = "detail", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonBackReference
	private List<PromotionsDetailEntity> promotionsDetail;
	
	public PromotionsModel entityToModel() {
		PromotionsModel model = new PromotionsModel();

		model.setId(this.getId());
		model.setPromotionCode(this.getPromotionCode());
		model.setModelCode(this.getModelCode());
		model.setDescription(this.getDescription());
		model.setCorporate(this.getCorporate());
		model.setPermanentValidity(this.getPermanentValidity());
		model.setStartDate(this.getStartDate());
		model.setEndDate(this.getEndDate());
		model.setPromotionsDetail(this.getPromotionsDetail());
		return model;
	}

	
//	public PromotionsEntity(Long id, String promotionCode, String modelCode, String description, String corporate,
//			String permanentValidity, LocalDateTime startDate, LocalDateTime endDate,
//			List<PromotionsDetailEntity> promotionsDetail) {
//		super();
//		this.id = id;
//		this.promotionCode = promotionCode;
//		this.modelCode = modelCode;
//		this.description = description;
//		this.corporate = corporate;
//		this.permanentValidity = permanentValidity;
//		this.startDate = startDate;
//		this.endDate = endDate;
//		this.promotionsDetail = promotionsDetail;
//	}


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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
