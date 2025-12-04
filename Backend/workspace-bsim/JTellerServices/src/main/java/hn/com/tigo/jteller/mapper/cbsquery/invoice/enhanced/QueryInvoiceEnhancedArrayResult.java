package hn.com.tigo.jteller.mapper.cbsquery.invoice.enhanced;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class QueryInvoiceEnhancedArrayResult {
	
	@SerializedName("ars:InvoiceInfo")
	private List<ArsInvoiceInfo> invoiceInfo;
	
	@SerializedName("TotalRowNum")
	private Integer totalRowNum;

	public List<ArsInvoiceInfo> getInvoiceInfo() {
		return invoiceInfo;
	}

	public void setInvoiceInfo(List<ArsInvoiceInfo> invoiceInfo) {
		this.invoiceInfo = invoiceInfo;
	}

	public Integer getTotalRowNum() {
		return totalRowNum;
	}

	public void setTotalRowNum(Integer totalRowNum) {
		this.totalRowNum = totalRowNum;
	}
	
	
}
