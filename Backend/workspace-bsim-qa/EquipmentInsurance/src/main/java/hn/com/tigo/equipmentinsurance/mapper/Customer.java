package hn.com.tigo.equipmentinsurance.mapper;

import com.google.gson.annotations.SerializedName;

public class Customer {
	@SerializedName("CustKey")
	private String custKey;

	@SerializedName("OrgInfo")
	private OrgInfo orgInfo;

	@SerializedName("IndividualInfo")
	private IndividualInfo individualInfo;

	public String getCustKey() {
		return custKey;
	}

	public void setCustKey(String custKey) {
		this.custKey = custKey;
	}

	public OrgInfo getOrgInfo() {
		return orgInfo;
	}

	public void setAcctInfo(OrgInfo orgInfo) {
		this.orgInfo = orgInfo;
	}

	public IndividualInfo getIndividualInfo() {
		return individualInfo;
	}

	public void setIndividualInfo(IndividualInfo individualInfo) {
		this.individualInfo = individualInfo;
	}

	@Override
	public String toString() {
		return "Customer [custKey=" + custKey + ", orgInfo=" + orgInfo + ", individualInfo=" + individualInfo + "]";
	}

}