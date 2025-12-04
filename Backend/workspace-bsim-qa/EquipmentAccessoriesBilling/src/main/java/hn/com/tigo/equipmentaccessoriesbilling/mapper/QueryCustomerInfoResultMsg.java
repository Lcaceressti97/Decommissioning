package hn.com.tigo.equipmentaccessoriesbilling.mapper;

import com.google.gson.annotations.SerializedName;

public class QueryCustomerInfoResultMsg {

	@SerializedName("QueryCustomerInfoResult")
	private QueryCustomerInfoResult queryCustomerInfoResult;

	@SerializedName("ResultHeader")
	private ResultHeader resultHeader;
	
	public QueryCustomerInfoResult getQueryCustomerInfoResult() {
		return queryCustomerInfoResult;
	}

	public void setQueryCustomerInfoResult(QueryCustomerInfoResult queryCustomerInfoResult) {
		this.queryCustomerInfoResult = queryCustomerInfoResult;
	}

	public ResultHeader getResultHeader() {
		return resultHeader;
	}

	public void setResultHeader(ResultHeader resultHeader) {
		this.resultHeader = resultHeader;
	}

	@Override
	public String toString() {
		return "QueryCustomerInfoResultMsg [queryCustomerInfoResult=" + queryCustomerInfoResult + ", resultHeader="
				+ resultHeader + "]";
	}
}
