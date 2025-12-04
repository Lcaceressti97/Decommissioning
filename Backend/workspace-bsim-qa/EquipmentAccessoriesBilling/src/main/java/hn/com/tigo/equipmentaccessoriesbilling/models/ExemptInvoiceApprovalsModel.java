package hn.com.tigo.equipmentaccessoriesbilling.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExemptInvoiceApprovalsModel {

	private Long id;

	private Long idInvoice;

	private String commentApproval;

	private String userApproved;

	private LocalDateTime approvalDate;

	private Long status;
}
