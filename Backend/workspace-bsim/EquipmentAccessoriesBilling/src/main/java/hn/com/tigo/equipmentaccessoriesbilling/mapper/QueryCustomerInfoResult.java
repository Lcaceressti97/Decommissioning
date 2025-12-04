package hn.com.tigo.equipmentaccessoriesbilling.mapper;

import com.google.gson.annotations.SerializedName;

public class QueryCustomerInfoResult {
    @SerializedName("Account")
    private Account account;

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "QueryCustomerInfoResult [account=" + account + "]";
	}
    
	
    
}
