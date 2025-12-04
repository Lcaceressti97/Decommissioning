package hn.com.tigo.equipmentblacklist.entities;

import lombok.*;
import javax.persistence.*;

import hn.com.tigo.equipmentblacklist.models.ImeiControlFileModel;

import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "BL_IMEI_CONTROL_FILE")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImeiControlFileEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "TRANSACTION_ID", length = 200)
	private String transactionId;

	@Column(name = "PHONE", length = 20)
	private String phone;

	@Column(name = "IMEI", length = 50)
	private String imei;

	@Column(name = "IMSI", length = 100)
	private String imsi;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "CREATED_DATE")
	private LocalDateTime createdDate;

	public ImeiControlFileModel entityToModel() {
		ImeiControlFileModel model = new ImeiControlFileModel();
		model.setId(this.getId());
		model.setTransactionId(this.getTransactionId());
		model.setPhone(this.getPhone());
		model.setImei(this.getImei());
		model.setImsi(this.getImsi());
		model.setStatus(this.getStatus());
		model.setCreatedDate(this.getCreatedDate());
		return model;
	}

}
