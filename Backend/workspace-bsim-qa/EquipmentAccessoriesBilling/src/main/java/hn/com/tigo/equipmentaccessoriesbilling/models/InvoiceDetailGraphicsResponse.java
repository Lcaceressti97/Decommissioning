package hn.com.tigo.equipmentaccessoriesbilling.models;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceDetailGraphicsResponse {

	private Long unauthorizedInvoices;
	private Long authorizedInvoices;
	private Long issuedInvoices;
	private Long invoicesWithTaxNumber;
	private Long canceledInvoicesWithoutIssued;
	private Long canceledInvoicesWithTaxNumber;
	private Long errorInvoice;
	private Map<String, Long> invoicesByType;
	private List<InvoiceTypeAndStatus> invoicesByTypeAndStatus;
	private List<BranchOfficeAndStatus> invoicesByBranchOfficeAndStatus;
	private Map<String, Long> invoicesPerSeller;
}
