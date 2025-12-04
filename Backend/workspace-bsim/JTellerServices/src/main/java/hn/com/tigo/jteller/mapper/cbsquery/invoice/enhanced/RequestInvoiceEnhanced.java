package hn.com.tigo.jteller.mapper.cbsquery.invoice.enhanced;

import hn.com.tigo.jteller.models.QueryObj;

public class RequestInvoiceEnhanced {
	private QueryObj queryObj;
	private InvoiceHeaderFilter invoiceHeaderFilter;
	private String retrieveDetail;
	private String totalRowNum;
	private String beginRowNum;
	private String fetchRowNum;
	public QueryObj getQueryObj() {
		return queryObj;
	}
	public void setQueryObj(QueryObj queryObj) {
		this.queryObj = queryObj;
	}
	public InvoiceHeaderFilter getInvoiceHeaderFilter() {
		return invoiceHeaderFilter;
	}
	public void setInvoiceHeaderFilter(InvoiceHeaderFilter invoiceHeaderFilter) {
		this.invoiceHeaderFilter = invoiceHeaderFilter;
	}
	public String getRetrieveDetail() {
		return retrieveDetail;
	}
	public void setRetrieveDetail(String retrieveDetail) {
		this.retrieveDetail = retrieveDetail;
	}
	public String getTotalRowNum() {
		return totalRowNum;
	}
	public void setTotalRowNum(String totalRowNum) {
		this.totalRowNum = totalRowNum;
	}
	public String getBeginRowNum() {
		return beginRowNum;
	}
	public void setBeginRowNum(String beginRowNum) {
		this.beginRowNum = beginRowNum;
	}
	public String getFetchRowNum() {
		return fetchRowNum;
	}
	public void setFetchRowNum(String fetchRowNum) {
		this.fetchRowNum = fetchRowNum;
	}
	
	
}
