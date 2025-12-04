package hn.com.tigo.equipmentaccessoriesbilling.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import hn.com.tigo.equipmentaccessoriesbilling.models.ControlAuthEmissionModel;

@Entity
@Table(name = "MEA_CONTROL_AUTH_EMISSIONS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ControlAuthEmissionEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CONTROL_AUT_EMI")
	@SequenceGenerator(name = "SQ_CONTROL_AUT_EMI", sequenceName = "SQ_CONTROL_AUT_EMI", allocationSize = 1)
	@Column(name = "ID", nullable = false, updatable = false)
	private Long id;

	@Column(name = "ID_PREFECTURE")
	private Long idPrefecture;

	@Column(name = "TYPE_APPROVAL")
	private Long typeApproval;

	@Lob
	@Column(name = "DESCRIPTION", columnDefinition = "CLOB")
	private String description;

	@Column(name = "PAYMENT_CODE", length = 100)
	private String paymentCode;
	
	@Column(name = "USER_CREATE", length = 50)
	private String userCreate;

	@Column(name = "CREATED", nullable = false)
	private LocalDateTime created;

	public ControlAuthEmissionModel entityToModel() {
		ControlAuthEmissionModel model = new ControlAuthEmissionModel();
		model.setId(this.getId());
		model.setIdPrefecture(this.getIdPrefecture());
		model.setTypeApproval(this.getTypeApproval());
		model.setDescription(this.getDescription());
		model.setPaymentCode(this.getPaymentCode());
		model.setUserCreate(this.getUserCreate());
		model.setCreated(this.getCreated());
		return model;
	}
}
