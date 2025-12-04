package hn.com.tigo.equipmentinsurance.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import hn.com.tigo.equipmentinsurance.models.DeductibleRatesModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "SE_DEDUCTIBLE_RATES")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeductibleRatesEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "MODEL", length = 100)
	private String model;

	@Column(name = "DESCRIPTION", length = 100)
	private String description;

	@Column(name = "FIRST_CLAIM", length = 100)
	private String firstClaim;

	@Column(name = "SECOND_CLAIM", length = 100)
	private String secondClaim;

	@Column(name = "THIRD_CLAIM", length = 100)
	private String thirdClaim;
	
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reason_id", nullable = false)
    private ReasonsEntity reason;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "CREATED", nullable = false)
	private LocalDateTime created;

	public DeductibleRatesModel entityToModel() {
		DeductibleRatesModel model = new DeductibleRatesModel();

		model.setId(this.getId());
		model.setModel(this.getModel());
		model.setDescription(this.getDescription());
		model.setFirstClaim(this.getFirstClaim());
		model.setSecondClaim(this.getSecondClaim());
		model.setThirdClaim(this.getThirdClaim());
		model.setReason(this.getReason().getId());
		model.setReasonDescription(this.getReason().getDescription());
		model.setStatus(this.getStatus());
		model.setCreated(this.getCreated());
		return model;
	}

}
