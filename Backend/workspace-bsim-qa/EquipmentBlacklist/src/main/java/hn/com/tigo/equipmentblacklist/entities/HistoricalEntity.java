package hn.com.tigo.equipmentblacklist.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

import hn.com.tigo.equipmentblacklist.models.HistoricalModel;
import lombok.*;

@Table(name = "BL_HISTORICAL")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricalEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "ESN", length = 40)
	private String esn;

	@Column(name = "ACCT_CODE")
	private Long acctCode;

	@Column(name = "PHONE", length = 20)
	private String phone;

	@Column(name = "LOCK_TYPE")
	private String lockType;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "CREATED_DATE")
	private LocalDateTime createdDate;

	@Column(name = "PROCESS_DATE")
	private LocalDateTime processDate;

	public HistoricalModel entityToModel() {
		HistoricalModel model = new HistoricalModel();
		model.setId(this.getId());
		model.setEsn(this.getEsn());
		model.setAcctCode(this.getAcctCode());
		model.setPhone(this.getPhone());
		model.setLockType(this.getLockType());
		model.setStatus(this.getStatus());
		model.setCreatedDate(this.getCreatedDate());
		model.setProcessDate(this.getProcessDate());
		return model;
	}
}
