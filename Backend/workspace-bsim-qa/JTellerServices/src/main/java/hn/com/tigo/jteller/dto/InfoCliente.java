package hn.com.tigo.jteller.dto;

public class InfoCliente {

	// Props
	private String accountCode;
	private String primaryIdentity;
	private String custCode;
	private String acctName;
	private String acctEmail;
	private String address;
	
	
	// Constructor
	public InfoCliente(){}
	
	
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public String getPrimaryIdentity() {
		return primaryIdentity;
	}
	public void setPrimaryIdentity(String primaryIdentity) {
		this.primaryIdentity = primaryIdentity;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getAcctName() {
		return acctName;
	}
	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}
	public String getAcctEmail() {
		return acctEmail;
	}
	public void setAcctEmail(String acctEmail) {
		this.acctEmail = acctEmail;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}


	@Override
	public String toString() {
		return "InfoCliente [accountCode=" + accountCode + ", primaryIdentity=" + primaryIdentity + ", custCode="
				+ custCode + ", acctName=" + acctName + ", acctEmail=" + acctEmail + ", address=" + address + "]";
	}
	
	
	
}
