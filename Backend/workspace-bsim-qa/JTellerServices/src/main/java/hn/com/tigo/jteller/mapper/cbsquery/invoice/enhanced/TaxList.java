package hn.com.tigo.jteller.mapper.cbsquery.invoice.enhanced;

import com.google.gson.annotations.SerializedName;

public class TaxList {
	
	@SerializedName("ars:TaxAmt")
	private Integer taxAmt;
	
	@SerializedName("ars:CurrencyId")
	private Integer currencyId;
	
	@SerializedName("ars:TaxCode")
	private Long taxCode;
	
	public Integer getTaxAmt() {
		return taxAmt;
	}
	public void setTaxAmt(Integer taxAmt) {
		this.taxAmt = taxAmt;
	}
	public Integer getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
	}
	public Long getTaxCode() {
		return taxCode;
	}
	public void setTaxCode(Long taxCode) {
		this.taxCode = taxCode;
	}
	@Override
	public String toString() {
		return "TaxList [taxAmt=" + taxAmt + ", currencyId=" + currencyId + ", taxCode=" + taxCode + "]";
	}
	
	
}
