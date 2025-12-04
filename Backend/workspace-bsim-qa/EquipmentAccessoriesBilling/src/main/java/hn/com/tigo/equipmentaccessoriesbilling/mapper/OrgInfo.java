package hn.com.tigo.equipmentaccessoriesbilling.mapper;

import com.google.gson.annotations.SerializedName;

public class OrgInfo {
	@SerializedName("OrgAddressKey")
	private String orgAddressKey;

	@SerializedName("IDNumber")
	private Long iDNumber;

	@SerializedName("OrgName")
	private String orgName;

	@SerializedName("IDValidity")
	private Long iDValidity;

	public String getOrgAddressKey() {
		return orgAddressKey;
	}

	public void setOrgAddressKey(String orgAddressKey) {
		this.orgAddressKey = orgAddressKey;
	}

	public Long getiDNumber() {
		return iDNumber;
	}

	public void setiDNumber(Long iDNumber) {
		this.iDNumber = iDNumber;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Long getiDValidity() {
		return iDValidity;
	}

	public void setiDValidity(Long iDValidity) {
		this.iDValidity = iDValidity;
	}

	@Override
	public String toString() {
		return "OrgInfo [orgAddressKey=" + orgAddressKey + ", iDNumber=" + iDNumber + ", orgName=" + orgName
				+ ", iDValidity=" + iDValidity + "]";
	}

	
	
}
