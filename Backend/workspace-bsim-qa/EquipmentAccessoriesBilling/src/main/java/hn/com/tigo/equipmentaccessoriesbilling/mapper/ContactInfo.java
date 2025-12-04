package hn.com.tigo.equipmentaccessoriesbilling.mapper;

import com.google.gson.annotations.SerializedName;

public class ContactInfo {
	@SerializedName("Email")
	private String email;

	@SerializedName("FirstName")
	private String firstName;

	@SerializedName("AddressKey")
	private Long addressKey;

	@SerializedName("MobilePhone")
	private Integer mobilePhone;

	@SerializedName("Title")
	private Integer title;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Long getAddressKey() {
		return addressKey;
	}

	public void setAddressKey(Long addressKey) {
		this.addressKey = addressKey;
	}

	public Integer getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(Integer mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Integer getTitle() {
		return title;
	}

	public void setTitle(Integer title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "ContactInfo [email=" + email + ", firstName=" + firstName + ", addressKey=" + addressKey
				+ ", mobilePhone=" + mobilePhone + ", title=" + title + "]";
	}

	
	
}
