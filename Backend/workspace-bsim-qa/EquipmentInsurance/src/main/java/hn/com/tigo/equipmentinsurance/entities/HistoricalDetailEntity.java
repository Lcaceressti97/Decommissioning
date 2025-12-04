package hn.com.tigo.equipmentinsurance.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.equipmentinsurance.models.HistoricalDetailModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "BL_HISTORICAL_DETAIL")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricalDetailEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "ID_HISTORICAL")
	private Long idHistorical;

	@Column(name = "ESN_IMEI", length = 40)
	private String esnImei;

	@Column(name = "IMEI_SIN_ULT_DIGIT", length = 40)
	private String imeiSinUltDigit;

	@Column(name = "OPERATOR", length = 3)
	private Long operator;

	@Column(name = "LOCK_TYPE", length = 50)
	private String lockType;

	@Column(name = "PHONE", length = 20)
	private String phone;

	@Column(name = "ACCT_CODE", length = 50)
	private Long acctCode;

	@Column(name = "TYPE_TECHNOLOGY", length = 1)
	private String typeTechnology;

	@Column(name = "SIMCARD", length = 30)
	private String simcard;

	@Column(name = "COMPLAINING_NAME", length = 100)
	private String complainingName;

	@Column(name = "COMPLAINING_IDENTITY", length = 20)
	private String complainingIdentity;

	@Column(name = "COMPLAINING_ADDRESS", length = 150)
	private String complainingAddress;

	@Column(name = "COMPLAINING_PHONE", length = 20)
	private String complainingPhone;

	@Column(name = "REASON_BLOCKING")
	private String reasonBlocking;

	@Column(name = "CREATED_DATE")
	private LocalDateTime createdDate;

	@Column(name = "UNLOCKING_NAME", length = 100)
	private String unlockingName;

	@Column(name = "UNLOCKING_IDENTIFY", length = 20)
	private String unlockingIdentity;

	@Column(name = "UNLOCKING_ADDRESS", length = 150)
	private String unlockingAddress;

	@Column(name = "UNLOCKING_PHONE", length = 20)
	private String unlockingPhone;

	@Column(name = "REASON_UNLOCK")
	private String reasonUnlock;

	@Column(name = "UNLOCK_DATE")
	private LocalDateTime unlockDate;

	@Column(name = "USER_TRANSACTION", length = 30)
	private String userTransaction;

	@Column(name = "TRANSACTION_SCREEN", length = 10)
	private String transactionScreen;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "STATUS_LOW_HIGH")
	private String statusLowHigh;

	@Column(name = "BLOCK_DATE")
	private LocalDateTime blockDate;

	@Column(name = "MODEL")
	private String model;

	public HistoricalDetailModel entityToModel() {
		HistoricalDetailModel model = new HistoricalDetailModel();
		model.setId(this.getId());
		model.setIdHistorical(this.getIdHistorical());
		model.setEsnImei(this.getEsnImei());
		model.setIvesn(this.getImeiSinUltDigit());
		model.setOperador(this.getOperator());
		model.setTipobloqueo(this.getLockType());
		model.setTelefono(this.getPhone());
		model.setAnexo(this.getAcctCode());
		model.setTechnologyType(this.getTypeTechnology());
		model.setSimcard(this.getSimcard());
		model.setNombreUsuarioTransaccion(this.getComplainingName());
		model.setIdentidadUsuarioTransaccion(this.getComplainingIdentity());
		model.setDireccionUsuarioTransaccion(this.getComplainingAddress());
		model.setTelefonoUsuarioTransaccion(this.getComplainingPhone());
		model.setAnoTransaccion(this.getCreatedDate().getYear());
		model.setMesTransaccion((long) this.getCreatedDate().getMonthValue());
		model.setDiaTransaccion((long) this.getCreatedDate().getDayOfMonth());
		model.setHoraTransaccion((long) this.getCreatedDate().getHour());
		model.setMotivoBloqueo(this.getReasonBlocking());
		model.setNombreDesbloqueante(this.getUnlockingName());
		model.setIdentidadDesbloqueante(this.getUnlockingIdentity());
		model.setDireccionDesbloqueante(this.getUnlockingAddress());
		model.setTelefonoDesbloqueante(this.getUnlockingPhone());
		model.setMotivoDesbloqueo(this.getReasonUnlock());
		model.setFechaDesbloqueo(this.getUnlockDate());
		model.setUsuarioTransaccion(this.getUserTransaction());
		model.setPantallaTransaccion(this.getTransactionScreen());
		model.setEstadoBajaAlta(this.getStatusLowHigh());
		model.setCreatedDate(this.getCreatedDate());
		model.setStatus(this.getStatus());
		model.setFechaBloqueo(this.blockDate);
		model.setModelo(this.getModel());
		return model;
	}

}
