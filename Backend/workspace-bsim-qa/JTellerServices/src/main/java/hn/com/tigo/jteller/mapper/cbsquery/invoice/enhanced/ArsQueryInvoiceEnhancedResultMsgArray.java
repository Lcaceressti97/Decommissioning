package hn.com.tigo.jteller.mapper.cbsquery.invoice.enhanced;

import com.google.gson.annotations.SerializedName;

public class ArsQueryInvoiceEnhancedResultMsgArray {
	@SerializedName("xmlns:ars")
	private String xmlnsArs;
	
	@SerializedName("xmlns:cbs")
	private String xmlnsCbs;
	
	@SerializedName("QueryInvoiceEnhancedResult")
	private QueryInvoiceEnhancedResultArray queryInvoiceEnhancedResultArray;
	
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

	public QueryInvoiceEnhancedResultArray getQueryInvoiceEnhancedResultArray() {
		return queryInvoiceEnhancedResultArray;
	}

	public void setQueryInvoiceEnhancedResultArray(QueryInvoiceEnhancedResultArray queryInvoiceEnhancedResultArray) {
		this.queryInvoiceEnhancedResultArray = queryInvoiceEnhancedResultArray;
	}

	public ResultHeader getResultHeader() {
		return resultHeader;
	}

	public void setResultHeader(ResultHeader resultHeader) {
		this.resultHeader = resultHeader;
	}
	
	
}
