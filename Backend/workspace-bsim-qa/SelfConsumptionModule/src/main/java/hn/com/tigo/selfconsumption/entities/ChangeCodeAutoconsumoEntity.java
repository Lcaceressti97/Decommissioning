package hn.com.tigo.selfconsumption.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import hn.com.tigo.selfconsumption.models.ChangeCodeAutoconsumoModel;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "CHARGE_CODE_AUTOCONSUMO")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeCodeAutoconsumoEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CHARGE_CODE_AUTOCONSUMO_SEQ")
	@SequenceGenerator(name = "CHARGE_CODE_AUTOCONSUMO_SEQ", sequenceName = "CHARGE_CODE_AUTOCONSUMO_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private Long id;

	@Column(name = "OFFERING_ID")
	private Long offeringId;

	@Column(name = "CHARGE_CODE", length = 100)
	private String chargeCode;

	@Column(name = "ITEM_NAME", length = 200)
	private String itemName;

	@Column(name = "USER_NAME", length = 100)
	private String userName;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "CREATEDATE", nullable = false)
	private LocalDateTime createDate;

	public ChangeCodeAutoconsumoModel entityToModel() {
		ChangeCodeAutoconsumoModel model = new ChangeCodeAutoconsumoModel();

		model.setId(this.getId());
		model.setOfferingId(this.getOfferingId());
		model.setChargeCode(this.getChargeCode());
		model.setItemName(this.getItemName());
		model.setUserName(this.getUserName());
		model.setStatus(this.getStatus());
		model.setCreateDate(this.getCreateDate());
		return model;
	}

}
