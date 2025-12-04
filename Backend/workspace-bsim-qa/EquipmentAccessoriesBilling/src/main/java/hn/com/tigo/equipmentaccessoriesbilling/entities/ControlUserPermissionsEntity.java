package hn.com.tigo.equipmentaccessoriesbilling.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import hn.com.tigo.equipmentaccessoriesbilling.models.ControlUserPermissionsModel;

@Table(name = "MEA_CONTROL_USER_PERMISSIONS")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ControlUserPermissionsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "ID_USER")
	private Long idUser;

	@Column(name = "NAME", length = 100)
	private String name;

	@Column(name = "USER_NAME", length = 100)
	private String userName;

	@Column(name = "EMAIL", length = 100)
	private String email;

	@Column(name = "GENERATE_BILL", length = 1)
	private String generateBill;

	@Column(name = "ISSUE_SELLER_INVOICE", length = 1)
	private String issueSellerInvoice;

	@Column(name = "AUTHORIZE_INVOICE", length = 1)
	private String authorizeInvoice;
	
	@Column(name = "TYPE_USER")
	private Long typeUser;
	
	@Column(name = "ASSIGN_DISCOUNT", length = 1)
	private String assignDiscount;

	@Column(name = "CREATED", nullable = false, columnDefinition = "DATE DEFAULT sysdate")
	private LocalDateTime created;

	public ControlUserPermissionsModel entityToModel() {
		ControlUserPermissionsModel model = new ControlUserPermissionsModel();
		model.setId(this.getId());
		model.setIdUser(this.getIdUser());
		model.setName(this.getName());
		model.setUserName(this.getUserName());
		model.setEmail(this.getEmail());
		model.setGenerateBill(this.getGenerateBill());
		model.setIssueSellerInvoice(this.getIssueSellerInvoice());
		model.setAuthorizeInvoice(this.getAuthorizeInvoice());
		model.setTypeUser(this.getTypeUser());
		model.setAssignDiscount(this.getAssignDiscount());
		model.setCreated(this.getCreated());
		return model;
	}
}
