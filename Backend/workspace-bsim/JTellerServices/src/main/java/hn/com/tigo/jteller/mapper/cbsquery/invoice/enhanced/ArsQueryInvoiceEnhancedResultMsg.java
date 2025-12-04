package hn.com.tigo.jteller.mapper.cbsquery.invoice.enhanced;

import com.google.gson.annotations.SerializedName;

public class ArsQueryInvoiceEnhancedResultMsg {
	

	@SerializedName("xmlns:ars")
	private String xmlnsArs;
	
	@SerializedName("xmlns:cbs")
	private String xmlnsCbs;
	
	@SerializedName("QueryInvoiceEnhancedResult")
	private QueryInvoiceEnhancedResult queryInvoiceEnhancedResult;
	
	@SerializedName("ResultHeader")
	private ResultHeader resultHeader;

	public String getXmlnsArs() {
		return xmlnsArs;
	}
	public void setXmlnsArs(String xmlnsArs) {
		this.xmlnsArs = xmlnsArs;
	}
	public String getXmlnsCbs() {
		return xmlnsCbs;
	}
	public void setXmlnsCbs(String xmlnsCbs) {
		this.xmlnsCbs = xmlnsCbs;
	}
	public QueryInvoiceEnhancedResult getQueryInvoiceEnhancedResult() {
		return queryInvoiceEnhancedResult;
	}
	public void setQueryInvoiceEnhancedResult(QueryInvoiceEnhancedResult queryInvoiceEnhancedResult) {
		this.queryInvoiceEnhancedResult = queryInvoiceEnhancedResult;
	}
	public ResultHeader getResultHeader() {
		return resultHeader;
	}
	public void setResultHeader(ResultHeader resultHeader) {
		this.resultHeader = resultHeader;
	}
	@Override
	public String toString() {
		return "ArsQueryInvoiceEnhancedResultMsg [xmlnsArs=" + xmlnsArs + ", xmlnsCbs=" + xmlnsCbs
				+ ", queryInvoiceEnhancedResult=" + queryInvoiceEnhancedResult + ", resultHeader=" + resultHeader + "]";
	}
	

	
	
}
