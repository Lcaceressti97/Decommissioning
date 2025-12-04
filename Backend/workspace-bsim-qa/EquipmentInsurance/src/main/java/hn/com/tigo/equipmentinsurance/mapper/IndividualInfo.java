package hn.com.tigo.equipmentinsurance.mapper;

import com.google.gson.annotations.SerializedName;

public class IndividualInfo {

	@SerializedName("IDNumber")
	private String iDNumber;

	@SerializedName("HomeAddressKey")
	private String homeAddressKey;

	@SerializedName("Gender")
	private Long gender;

	@SerializedName("IDType")
	private Long iDType;

	@SerializedName("FirstName")
	private String firstName;

	@SerializedName("LastName")
	private String lastName;

	@SerializedName("IDValidity")
	private Long iDValidity;

	public String getiDNumber() {
		return iDNumber;
	}

	public void setiDNumber(String iDNumber) {
		this.iDNumber = iDNumber;
	}

	public String getHomeAddressKey() {
		return homeAddressKey;
	}

	public void setHomeAddressKey(String homeAddressKey) {
		this.homeAddressKey = homeAddressKey;
	}

	public Long getGender() {
		return gender;
	}

	public void setGender(Long gender) {
		this.gender = gender;
	}

	public Long getiDType() {
		return iDType;
	}

	public void setiDType(Long iDType) {
		this.iDType = iDType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getiDValidity() {
		return iDValidity;
	}

	public void setiDValidity(Long iDValidity) {
		this.iDValidity = iDValidity;
	}

	@Override
	public String toString() {
		return "IndividualInfo [iDNumber=" + iDNumber + ", homeAddressKey=" + homeAddressKey + ", gender=" + gender
				+ ", iDType=" + iDType + ", firstName=" + firstName + ", lastName=" + lastName + ", iDValidity="
				+ iDValidity + "]";
	}

}
