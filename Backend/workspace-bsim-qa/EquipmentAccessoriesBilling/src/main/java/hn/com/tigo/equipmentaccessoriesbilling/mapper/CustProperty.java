package hn.com.tigo.equipmentaccessoriesbilling.mapper;

import com.google.gson.annotations.SerializedName;

public class CustProperty {
	@SerializedName("Value")
    private String value;

    @SerializedName("Code")
    private String code;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "CustProperty [value=" + value + ", code=" + code + "]";
	}
    
    
	
	
}
