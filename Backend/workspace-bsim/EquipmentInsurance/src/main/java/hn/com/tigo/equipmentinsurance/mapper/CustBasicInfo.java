package hn.com.tigo.equipmentinsurance.mapper;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class CustBasicInfo {
	@SerializedName("CustLoyalty")
	private String custLoyalty;

	@SerializedName("CustProperty")
	private List<CustProperty> custProperty;

	@SerializedName("DFTCurrencyID")
	private Integer dFTCurrencyID;

	@SerializedName("DFTWrittenLang")
	private Integer dFTWrittenLang;

	@SerializedName("CustSegment")
	private String custSegment;

	@SerializedName("CustLevel")
	private Integer custLevel;

	@SerializedName("DunningFlag")
	private Integer dunningFlag;

	@SerializedName("DFTIVRLang")
	private Integer DFTIVRLang;

	public String getCustLoyalty() {
		return custLoyalty;
	}

	public void setCustLoyalty(String custLoyalty) {
		this.custLoyalty = custLoyalty;
	}

	public List<CustProperty> getCustProperty() {
		return custProperty;
	}

	public void setCustProperty(List<CustProperty> custProperty) {
		this.custProperty = custProperty;
	}

	public Integer getdFTCurrencyID() {
		return dFTCurrencyID;
	}

	public void setdFTCurrencyID(Integer dFTCurrencyID) {
		this.dFTCurrencyID = dFTCurrencyID;
	}

	public Integer getdFTWrittenLang() {
		return dFTWrittenLang;
	}

	public void setdFTWrittenLang(Integer dFTWrittenLang) {
		this.dFTWrittenLang = dFTWrittenLang;
	}

	public String getCustSegment() {
		return custSegment;
	}

	public void setCustSegment(String custSegment) {
		this.custSegment = custSegment;
	}

	public Integer getCustLevel() {
		return custLevel;
	}

	public void setCustLevel(Integer custLevel) {
		this.custLevel = custLevel;
	}

	public Integer getDunningFlag() {
		return dunningFlag;
	}

	public void setDunningFlag(Integer dunningFlag) {
		this.dunningFlag = dunningFlag;
	}

	public Integer getDFTIVRLang() {
		return DFTIVRLang;
	}

	public void setDFTIVRLang(Integer dFTIVRLang) {
		DFTIVRLang = dFTIVRLang;
	}

	@Override
	public String toString() {
		return "CustBasicInfo [custLoyalty=" + custLoyalty + ", custProperty=" + custProperty + ", dFTCurrencyID="
				+ dFTCurrencyID + ", dFTWrittenLang=" + dFTWrittenLang + ", custSegment=" + custSegment + ", custLevel="
				+ custLevel + ", dunningFlag=" + dunningFlag + ", DFTIVRLang=" + DFTIVRLang + "]";
	}

	
	
}
