package hn.com.tigo.jteller.mapper.cbsquery.invoice.enhanced;

import com.google.gson.annotations.SerializedName;

public class InvoiceEnhanced {
	
	@SerializedName("ars:QueryInvoiceEnhancedResultMsg")
	private ArsQueryInvoiceEnhancedResultMsg arsQueryInvoiceEnhancedResultMsg;

	public ArsQueryInvoiceEnhancedResultMsg getArsQueryInvoiceEnhancedResultMsg() {
		return arsQueryInvoiceEnhancedResultMsg;
	}

	public void setArsQueryInvoiceEnhancedResultMsg(ArsQueryInvoiceEnhancedResultMsg arsQueryInvoiceEnhancedResultMsg) {
		this.arsQueryInvoiceEnhancedResultMsg = arsQueryInvoiceEnhancedResultMsg;
	}

	@Override
	public String toString() {
		return "InvoiceEnhanced [arsQueryInvoiceEnhancedResultMsg=" + arsQueryInvoiceEnhancedResultMsg + "]";
	}


	
	
}
