package hn.com.tigo.equipmentinsurance.mapper;

import com.google.gson.annotations.SerializedName;


public class UserCustomer {
	@SerializedName("OrgInfo")
    private Object orgInfo;

    @SerializedName("CustKey")
    private String custKey;

    @SerializedName("CustInfo")
    private Object custInfo;

	public Object getOrgInfo() {
		return orgInfo;
	}

	public void setOrgInfo(OrgInfo orgInfo) {
		this.orgInfo = orgInfo;
	}

	public String getCustKey() {
		return custKey;
	}

	public void setCustKey(String custKey) {
		this.custKey = custKey;
	}

	public Object getCustInfo() {
		return custInfo;
	}

	public void setCustInfo(CustInfo custInfo) {
		this.custInfo = custInfo;
	}

	@Override
	public String toString() {
		return "UserCustomer [orgInfo=" + orgInfo + ", custKey=" + custKey + ", custInfo=" + custInfo + "]";
	}
    
    
}

