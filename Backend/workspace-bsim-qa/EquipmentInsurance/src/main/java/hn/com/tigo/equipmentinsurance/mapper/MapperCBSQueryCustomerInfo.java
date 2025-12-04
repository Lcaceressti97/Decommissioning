package hn.com.tigo.equipmentinsurance.mapper;

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
	
	

}
