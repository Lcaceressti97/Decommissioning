package hn.com.tigo.jteller.mapper.cbsquery.invoice.enhanced;

import com.google.gson.annotations.SerializedName;

public class InvoiceEnhancedArray {
	@SerializedName("ars:QueryInvoiceEnhancedResultMsg")
	private ArsQueryInvoiceEnhancedResultMsgArray arsQueryInvoiceEnhancedResultMsg;

	public ArsQueryInvoiceEnhancedResultMsgArray getArsQueryInvoiceEnhancedResultMsg() {
		return arsQueryInvoiceEnhancedResultMsg;
	}

	public void setArsQueryInvoiceEnhancedResultMsg(ArsQueryInvoiceEnhancedResultMsgArray arsQueryInvoiceEnhancedResultMsg) {
		this.arsQueryInvoiceEnhancedResultMsg = arsQueryInvoiceEnhancedResultMsg;
	}

	@Override
	public String toString() {
		return "InvoiceEnhanced [arsQueryInvoiceEnhancedResultMsg=" + arsQueryInvoiceEnhancedResultMsg + "]";
	}
}
