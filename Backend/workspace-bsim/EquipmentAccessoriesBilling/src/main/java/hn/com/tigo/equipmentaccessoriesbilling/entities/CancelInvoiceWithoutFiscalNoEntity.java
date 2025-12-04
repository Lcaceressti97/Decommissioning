package hn.com.tigo.equipmentaccessoriesbilling.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.equipmentaccessoriesbilling.models.CancelInvoiceWithoutFiscalNoModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "MEA_CANCEL_INVOICE_WITHOUT_FN")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelInvoiceWithoutFiscalNoEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false, updatable = false)
	private Long id;

	@Column(name = "ID_USER")
	private Long idUser;

	@Column(name = "USER_NAME", length = 100)
	private String userName;

	@Column(name = "PERMIT_STATUS", length = 1)
	private String permitStatus;

	@Column(name = "CREATED", nullable = false)
	private LocalDateTime created;

	public CancelInvoiceWithoutFiscalNoModel entityToModel() {
		CancelInvoiceWithoutFiscalNoModel model = new CancelInvoiceWithoutFiscalNoModel();
		model.setId(this.getId());
		model.setIdUser(this.getIdUser());
		model.setUserName(this.getUserName());
		model.setPermitStatus(this.getPermitStatus());
		model.setCreated(this.getCreated());
		return model;
	}

}
