package hn.com.tigo.jteller.mapper.cbsquery.invoice.enhanced;

import com.google.gson.annotations.SerializedName;

public class ArsAdditionalProperty {
	
	@SerializedName("arc:Value")
	private String arcValue;
	
	@SerializedName("arc:Code")
	private String arcCode;
	public String getArcValue() {
		return arcValue;
	}
	public void setArcValue(String arcValue) {
		this.arcValue = arcValue;
	}
	public String getArcCode() {
		return arcCode;
	}
	public void setArcCode(String arcCode) {
		this.arcCode = arcCode;
	}
	@Override
	public String toString() {
		return "ArsAdditionalProperty [arcValue=" + arcValue + ", arcCode=" + arcCode + "]";
	}
	
	
}
