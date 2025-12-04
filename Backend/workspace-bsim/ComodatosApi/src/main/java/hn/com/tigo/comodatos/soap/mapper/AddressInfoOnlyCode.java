package hn.com.tigo.comodatos.soap.mapper;

import com.google.gson.annotations.SerializedName;

public class AddressInfoOnlyCode {
	@SerializedName("PostCode")
	private String postCode;
	@SerializedName("Address1")
	private String addres1;
	@SerializedName("AddressKey")
	private String addressKey;

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

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
