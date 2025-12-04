package hn.com.tigo.jteller.mapper.cbsquery.customer.info;

import com.google.gson.annotations.SerializedName;

public class BillCycleInfo {
	@SerializedName("BillCycleType")
	private String billCycleType;

	@SerializedName("ExpireTime")
	private Long expireTime;

	@SerializedName("EffectiveTime")
	private Long effectiveTime;

	public String getBillCycleType() {
		return billCycleType;
	}

	public void setBillCycleType(String billCycleType) {
		this.billCycleType = billCycleType;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	public Long getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Long effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	@Override
	public String toString() {
		return "BillCycleInfo [billCycleType=" + billCycleType + ", expireTime=" + expireTime + ", effectiveTime="
				+ effectiveTime + "]";
	}

	
	
}
