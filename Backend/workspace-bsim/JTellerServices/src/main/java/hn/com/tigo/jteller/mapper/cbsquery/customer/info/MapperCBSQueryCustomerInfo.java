package hn.com.tigo.jteller.mapper.cbsquery.customer.info;

import com.google.gson.annotations.SerializedName;

public class MapperCBSQueryCustomerInfo {
	@SerializedName("QueryCustomerInfoResultMsg")
	private QueryCustomerInfoResultMsg queryCustomerInfoResultMsg;

	public QueryCustomerInfoResultMsg getQueryCustomerInfoResultMsg() {
		return queryCustomerInfoResultMsg;
	}

	public void setQueryCustomerInfoResultMsg(QueryCustomerInfoResultMsg queryCustomerInfoResultMsg) {
		this.queryCustomerInfoResultMsg = queryCustomerInfoResultMsg;
	}

	@Override
	public String toString() {
		return "MapperCBS{" + "queryCustomerInfoResultMsg=" + queryCustomerInfoResultMsg + '}';
	}
}
