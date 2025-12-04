package hn.com.tigo.comodatos.soap.mapper;

import com.google.gson.annotations.SerializedName;

public class AddressInfo {
	@SerializedName("Address3")
	private String address3;

	@SerializedName("Address2")
	private String address2;

	@SerializedName("Address1")
	private String address1;

	@SerializedName("Address4")
	private String address4;

	@SerializedName("AddressKey")
	private Long addressKey;

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress4() {
		return address4;
	}

	public void setAddress4(String address4) {
		this.address4 = address4;
	}

	public Long getAddressKey() {
		return addressKey;
	}

	public void setAddressKey(Long addressKey) {
		this.addressKey = addressKey;
	}

	@Override
	public String toString() {
		return "AddressInfo [address3=" + address3 + ", address2=" + address2 + ", address1=" + address1 + ", address4="
				+ address4 + ", addressKey=" + addressKey + "]";
	}

	
	
}
