package hn.com.tigo.comodatos.soap.mapper;

import com.google.gson.annotations.SerializedName;

public class FreeBillMedium {
	@SerializedName("BMType")
	private Integer bMType;

	@SerializedName("BMCode")
	private Integer bMCode;

	public Integer getbMType() {
		return bMType;
	}

	public void setbMType(Integer bMType) {
		this.bMType = bMType;
	}

	public Integer getbMCode() {
		return bMCode;
	}

	public void setbMCode(Integer bMCode) {
		this.bMCode = bMCode;
	}

	@Override
	public String toString() {
		return "FreeBillMedium [bMType=" + bMType + ", bMCode=" + bMCode + "]";
	}

	
	
}
