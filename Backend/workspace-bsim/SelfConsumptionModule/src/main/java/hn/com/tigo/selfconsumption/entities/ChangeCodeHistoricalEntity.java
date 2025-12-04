package hn.com.tigo.selfconsumption.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import hn.com.tigo.selfconsumption.models.ChangeCodeHistoricalModel;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "CHARGE_CODE_HISTORICAL")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeCodeHistoricalEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "CHARGE_CODE_ID")
	private Long chargeCodeId;

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
	
	@Column(name = "ACTION", length = 100)
	private String action;

	public ChangeCodeHistoricalModel entityToModel() {
		ChangeCodeHistoricalModel model = new ChangeCodeHistoricalModel();

		model.setId(this.getId());
		model.setChargeCodeId(this.getChargeCodeId());
		model.setChargeCode(this.getChargeCode());
		model.setItemName(this.getItemName());
		model.setUserName(this.getUserName());
		model.setStatus(this.getStatus());
		model.setCreateDate(this.getCreateDate());
		model.setAction(this.getAction());
		return model;
	}
}
