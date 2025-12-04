package hn.com.tigo.equipmentinsurance.mapper;

import com.google.gson.annotations.SerializedName;

public class QueryCustomerInfoResult {
	@SerializedName("Customer")
	private Customer customer;

	@SerializedName("Account")
	private Account account;

	public Customer getCustomer() {
		return customer;
	}

	public void setAccount(Customer customer) {
		this.customer = customer;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "QueryCustomerInfoResult [customer=" + customer + "]";
	}



}