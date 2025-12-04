package hn.com.tigo.jteller.mapper.cbsquery.invoice.enhanced;

import com.google.gson.annotations.SerializedName;

public class ResultHeader {
	
	@SerializedName("cbs:Version")
	private Integer cbsVersion;
	
	@SerializedName("cbs:ResultCode")
	private Integer cbsResultCode;
	
	@SerializedName("cbs:MsgLanguageCode")
	private Integer cbsMsgLanguageCode;
	
	@SerializedName("cbs:ResultDesc")
	private String cbsResultDesc;
	
	public Integer getCbsVersion() {
		return cbsVersion;
	}
	public void setCbsVersion(Integer cbsVersion) {
		this.cbsVersion = cbsVersion;
	}
	public Integer getCbsResultCode() {
		return cbsResultCode;
	}
	public void setCbsResultCode(Integer cbsResultCode) {
		this.cbsResultCode = cbsResultCode;
	}
	public Integer getCbsMsgLanguageCode() {
		return cbsMsgLanguageCode;
	}
	public void setCbsMsgLanguageCode(Integer cbsMsgLanguageCode) {
		this.cbsMsgLanguageCode = cbsMsgLanguageCode;
	}
	public String getCbsResultDesc() {
		return cbsResultDesc;
	}
	public void setCbsResultDesc(String cbsResultDesc) {
		this.cbsResultDesc = cbsResultDesc;
	}
	@Override
	public String toString() {
		return "ResultHeader [cbsVersion=" + cbsVersion + ", cbsResultCode=" + cbsResultCode + ", cbsMsgLanguageCode="
				+ cbsMsgLanguageCode + ", cbsResultDesc=" + cbsResultDesc + "]";
	}
	
	
}
