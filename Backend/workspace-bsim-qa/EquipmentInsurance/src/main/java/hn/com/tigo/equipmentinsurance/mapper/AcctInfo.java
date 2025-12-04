package hn.com.tigo.equipmentinsurance.mapper;

import java.util.Map;

import com.google.gson.annotations.SerializedName;


public class AcctInfo {
    @SerializedName("BillCycleType")
    private String billCycleType;

    @SerializedName("AcctBasicInfo")
    private AcctBasicInfo acctBasicInfo;

    @SerializedName("CurrencyID")
    private Integer currencyID;

    @SerializedName("AcctCode")
    private Long acctCode;

    @SerializedName("UserCustomer")
    private UserCustomer userCustomer;

    @SerializedName("AcctType")
    private Integer acctType;

    @SerializedName("PaymentType")
    private Integer paymentType;

    @SerializedName("AcctPayMethod")
    private Integer acctPayMethod;

    @SerializedName("BillCycleInfo")
    private BillCycleInfo billCycleInfo;

    @SerializedName("AcctClass")
    private Integer acctClass;

    @SerializedName("BillCycleEndDate")
    private Long billCycleEndDate;

    @SerializedName("BillCycleOpenDate")
    private Long billCycleOpenDate;

    @SerializedName("UserCustomerKey")
    private String userCustomerKey;

    @SerializedName("RootAcctKey")
    private Long rootAcctKey;

    @SerializedName("AddressInfo")
    private Object addressInfo;

	public String getBillCycleType() {
		return billCycleType;
	}

	public void setBillCycleType(String billCycleType) {
		this.billCycleType = billCycleType;
	}

	public AcctBasicInfo getAcctBasicInfo() {
		return acctBasicInfo;
	}

	public void setAcctBasicInfo(AcctBasicInfo acctBasicInfo) {
		this.acctBasicInfo = acctBasicInfo;
	}

	public Integer getCurrencyID() {
		return currencyID;
	}

	public void setCurrencyID(Integer currencyID) {
		this.currencyID = currencyID;
	}

	public Long getAcctCode() {
		return acctCode;
	}

	public void setAcctCode(Long acctCode) {
		this.acctCode = acctCode;
	}

	public UserCustomer getUserCustomer() {
		return userCustomer;
	}

	public void setUserCustomer(UserCustomer userCustomer) {
		this.userCustomer = userCustomer;
	}

	public Integer getAcctType() {
		return acctType;
	}

	public void setAcctType(Integer acctType) {
		this.acctType = acctType;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public Integer getAcctPayMethod() {
		return acctPayMethod;
	}

	public void setAcctPayMethod(Integer acctPayMethod) {
		this.acctPayMethod = acctPayMethod;
	}

	public BillCycleInfo getBillCycleInfo() {
		return billCycleInfo;
	}

	public void setBillCycleInfo(BillCycleInfo billCycleInfo) {
		this.billCycleInfo = billCycleInfo;
	}

	public Integer getAcctClass() {
		return acctClass;
	}

	public void setAcctClass(Integer acctClass) {
		this.acctClass = acctClass;
	}

	public Long getBillCycleEndDate() {
		return billCycleEndDate;
	}

	public void setBillCycleEndDate(Long billCycleEndDate) {
		this.billCycleEndDate = billCycleEndDate;
	}

	public Long getBillCycleOpenDate() {
		return billCycleOpenDate;
	}

	public void setBillCycleOpenDate(Long billCycleOpenDate) {
		this.billCycleOpenDate = billCycleOpenDate;
	}

	public String getUserCustomerKey() {
		return userCustomerKey;
	}

	public void setUserCustomerKey(String userCustomerKey) {
		this.userCustomerKey = userCustomerKey;
	}

	public Long getRootAcctKey() {
		return rootAcctKey;
	}

	public void setRootAcctKey(Long rootAcctKey) {
		this.rootAcctKey = rootAcctKey;
	}

	public Object getAddressInfo() {
		return addressInfo;
	}

	public void setAddressInfo(Object addressInfo) {
		this.addressInfo = addressInfo;
	}

	@Override
	public String toString() {
		return "AcctInfo [billCycleType=" + billCycleType + ", acctBasicInfo=" + acctBasicInfo + ", currencyID="
				+ currencyID + ", acctCode=" + acctCode + ", userCustomer=" + userCustomer + ", acctType=" + acctType
				+ ", paymentType=" + paymentType + ", acctPayMethod=" + acctPayMethod + ", billCycleInfo="
				+ billCycleInfo + ", acctClass=" + acctClass + ", billCycleEndDate=" + billCycleEndDate
				+ ", billCycleOpenDate=" + billCycleOpenDate + ", userCustomerKey=" + userCustomerKey + ", rootAcctKey="
				+ rootAcctKey + ", addressInfo=" + addressInfo + "]";
	}
    
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getAddressInfoAsMap() {
	    if (addressInfo instanceof Map) {
	        return (Map<String, String>) addressInfo;
	    }
	    return null;
	}
    
}
