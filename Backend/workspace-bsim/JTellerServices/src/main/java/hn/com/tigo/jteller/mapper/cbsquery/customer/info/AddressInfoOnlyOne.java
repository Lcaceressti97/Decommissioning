package hn.com.tigo.jteller.mapper.cbsquery.customer.info;

import com.google.gson.annotations.SerializedName;

public class AddressInfoOnlyOne {
    @SerializedName("Address1")
    private String addres1;
    @SerializedName("AddressKey")
    private String addressKey;
	public String getAddres1() {
		return addres1;
	}
	public void setAddres1(String addres1) {
		this.addres1 = addres1;
	}
	public String getAddressKey() {
		return addressKey;
	}
	public void setAddressKey(String addressKey) {
		this.addressKey = addressKey;
	}
    
    
    
}
