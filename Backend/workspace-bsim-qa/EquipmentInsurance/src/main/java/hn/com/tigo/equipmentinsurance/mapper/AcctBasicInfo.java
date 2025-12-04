package hn.com.tigo.equipmentinsurance.mapper;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class AcctBasicInfo {
	@SerializedName("RedlistFlag")
	private Integer redlistFlag;

	@SerializedName("AcctName")
	private String acctName;

	@SerializedName("LateFeeChargeable")
	private String lateFeeChargeable;

	@SerializedName("ContactInfo")
	private ContactInfo contactInfo;

	@SerializedName("AcctProperty")
	private List<AcctProperty> acctProperty;

	@SerializedName("FreeBillMedium")
	private Object freeBillMedium;

	@SerializedName("BillLang")
	private Integer billLang;

	@SerializedName("DunningFlag")
	private Integer dunningFlag;

	public Integer getRedlistFlag() {
		return redlistFlag;
	}

	public void setRedlistFlag(Integer redlistFlag) {
		this.redlistFlag = redlistFlag;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getLateFeeChargeable() {
		return lateFeeChargeable;
	}

	public void setLateFeeChargeable(String lateFeeChargeable) {
		this.lateFeeChargeable = lateFeeChargeable;
	}

	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

	public List<AcctProperty> getAcctProperty() {
		return acctProperty;
	}

	public void setAcctProperty(List<AcctProperty> acctProperty) {
		this.acctProperty = acctProperty;
	}

	public Object getFreeBillMedium() {
		return freeBillMedium;
	}

	public void setFreeBillMedium(Object freeBillMedium) {
		this.freeBillMedium = freeBillMedium;
	}

	public Integer getBillLang() {
		return billLang;
	}

	public void setBillLang(Integer billLang) {
		this.billLang = billLang;
	}

	public Integer getDunningFlag() {
		return dunningFlag;
	}

	public void setDunningFlag(Integer dunningFlag) {
		this.dunningFlag = dunningFlag;
	}

	@Override
	public String toString() {
		return "AcctBasicInfo [redlistFlag=" + redlistFlag + ", acctName=" + acctName + ", lateFeeChargeable="
				+ lateFeeChargeable + ", contactInfo=" + contactInfo + ", acctProperty=" + acctProperty
				+ ", freeBillMedium=" + freeBillMedium + ", billLang=" + billLang + ", dunningFlag=" + dunningFlag
				+ "]";
	}

}
