package hn.com.tigo.jteller.mapper.cbsquery.invoice.enhanced;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class QueryInvoiceEnhancedResultArray {
	@SerializedName("ars:InvoiceInfo")
	private List<ArsInvoiceInfoArray> invoiceInfo;
	
	@SerializedName("ars:TotalRowNum")
	private Integer totalRowNum;

	public List<ArsInvoiceInfoArray> getInvoiceInfo() {
		return invoiceInfo;
	}

	public void setInvoiceInfo(List<ArsInvoiceInfoArray> invoiceInfo) {
		this.invoiceInfo = invoiceInfo;
	}

	public Integer getTotalRowNum() {
		return totalRowNum;
	}

	public void setTotalRowNum(Integer totalRowNum) {
		this.totalRowNum = totalRowNum;
	}
	
	
}
