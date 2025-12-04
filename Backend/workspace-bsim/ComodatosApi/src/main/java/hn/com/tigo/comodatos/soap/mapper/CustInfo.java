package hn.com.tigo.comodatos.soap.mapper;

import com.google.gson.annotations.SerializedName;

public class CustInfo {
	@SerializedName("CustType")
	private Integer custType;

	@SerializedName("CustNodeType")
	private Integer custNodeType;

	@SerializedName("CustClass")
	private Integer custClass;

	@SerializedName("CustCode")
	private String custCode;

	@SerializedName("CustBasicInfo")
	private CustBasicInfo custBasicInfo;

	public Integer getCustType() {
		return custType;
	}

	public void setCustType(Integer custType) {
		this.custType = custType;
	}

	public Integer getCustNodeType() {
		return custNodeType;
	}

	public void setCustNodeType(Integer custNodeType) {
		this.custNodeType = custNodeType;
	}

	public Integer getCustClass() {
		return custClass;
	}

	public void setCustClass(Integer custClass) {
		this.custClass = custClass;
	}

	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	public CustBasicInfo getCustBasicInfo() {
		return custBasicInfo;
	}

	public void setCustBasicInfo(CustBasicInfo custBasicInfo) {
		this.custBasicInfo = custBasicInfo;
	}

	@Override
	public String toString() {
		return "CustInfo [custType=" + custType + ", custNodeType=" + custNodeType + ", custClass=" + custClass
				+ ", custCode=" + custCode + ", custBasicInfo=" + custBasicInfo + "]";
	}

	
	
}
