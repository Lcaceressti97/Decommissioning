package hn.com.tigo.equipmentaccessoriesbilling.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.equipmentaccessoriesbilling.models.ExemptInvoiceApprovalsModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "MEA_EXEMPT_INVOICE_APPROVALS")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExemptInvoiceApprovalsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "ID_INVOICE")
	private Long idInvoice;

	@Column(name = "COMMENT_APPROVAL", length = 100)
	private String commentApproval;

	@Column(name = "USER_APPROVED", length = 50)
	private String userApproved;

	@Column(name = "APPROVAL_DATE")
	private LocalDateTime approvalDate;

	@Column(name = "STATUS")
	private Long status;

	public ExemptInvoiceApprovalsModel entityToModel() {
		ExemptInvoiceApprovalsModel model = new ExemptInvoiceApprovalsModel();
		model.setId(this.getId());
		model.setIdInvoice(this.getIdInvoice());
		model.setCommentApproval(this.getCommentApproval());
		model.setUserApproved(this.getUserApproved());
		model.setApprovalDate(this.getApprovalDate());
		model.setStatus(this.getStatus());
		return model;
	}

}
