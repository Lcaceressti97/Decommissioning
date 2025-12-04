package hn.com.tigo.jteller.mapper.cbsquery.invoice.enhanced;


import com.google.gson.annotations.SerializedName;

public class ArsInvoiceInfoArray {
	@SerializedName("ars:BillCycleID")
	private Long arsBillCycleID;
	
	@SerializedName("ars:AdditionalProperty")
	private Object arsAdditionalProperty;
	
	@SerializedName("ars:OpenAmount")
	private Long arsOpenAmount;
	
	@SerializedName("ars:AcctKey")
	private Long arsAcctKey;
	
	@SerializedName("ars:InvoiceNo")
	private String arsInvoiceNo;
	
	@SerializedName("ars:SubKey")
	private Long arsSubKey;
	
	@SerializedName("ars:PrimaryIdentity")
	private Integer arsPrimaryIdentity;
	
	@SerializedName("ars:TransType")
	private String arsTransType;
	
	@SerializedName("ars:BillCycleBeginTime")
	private Long arsBillCycleBeginTime;
	
	@SerializedName("ars:CustKey")
	private String arsCustKey;
	
	@SerializedName("ars:TAXAmount")
	private Long arsTAXAmount;
	
	@SerializedName("ars:CurrencyID")
	private Integer arsCurrencyID;
	
	@SerializedName("ars:InvoiceID")
	private Integer arsInvoiceID;
	
	@SerializedName("ars:Status")
	private String arsStatus;
	
	@SerializedName("ars:NPE")
	private String arsNPE;
	
	@SerializedName("ars:BillCycleEndTime")
	private Long arsBillCycleEndTime;
	
	@SerializedName("ars:InvoiceAmount")
	private Long arsInvoiceAmount;
	
	@SerializedName("ars:InvoiceType")
	private String arsInvoiceType;
	
	@SerializedName("ars:OpenTaxAmount")
	private Long arsOpenTaxAmount;
	
	@SerializedName("ars:InvoiceDate")
	private Long arsInvoiceDate;
	
	@SerializedName("ars:DueDate")
	private Long arsDueDate;

	public Long getArsBillCycleID() {
		return arsBillCycleID;
	}

	public void setArsBillCycleID(Long arsBillCycleID) {
		this.arsBillCycleID = arsBillCycleID;
	}

	public Object getArsAdditionalProperty() {
		return arsAdditionalProperty;
	}
	

	public void setArsAdditionalProperty(Object arsAdditionalProperty) {
		this.arsAdditionalProperty = arsAdditionalProperty;
	}

	public Long getArsOpenAmount() {
		return arsOpenAmount;
	}

	public void setArsOpenAmount(Long arsOpenAmount) {
		this.arsOpenAmount = arsOpenAmount;
	}

	public Long getArsAcctKey() {
		return arsAcctKey;
	}

	public void setArsAcctKey(Long arsAcctKey) {
		this.arsAcctKey = arsAcctKey;
	}

	public String getArsInvoiceNo() {
		return arsInvoiceNo;
	}

	public void setArsInvoiceNo(String arsInvoiceNo) {
		this.arsInvoiceNo = arsInvoiceNo;
	}

	public Long getArsSubKey() {
		return arsSubKey;
	}

	public void setArsSubKey(Long arsSubKey) {
		this.arsSubKey = arsSubKey;
	}

	public Integer getArsPrimaryIdentity() {
		return arsPrimaryIdentity;
	}

	public void setArsPrimaryIdentity(Integer arsPrimaryIdentity) {
		this.arsPrimaryIdentity = arsPrimaryIdentity;
	}

	public String getArsTransType() {
		return arsTransType;
	}

	public void setArsTransType(String arsTransType) {
		this.arsTransType = arsTransType;
	}

	public Long getArsBillCycleBeginTime() {
		return arsBillCycleBeginTime;
	}

	public void setArsBillCycleBeginTime(Long arsBillCycleBeginTime) {
		this.arsBillCycleBeginTime = arsBillCycleBeginTime;
	}

	public String getArsCustKey() {
		return arsCustKey;
	}

	public void setArsCustKey(String arsCustKey) {
		this.arsCustKey = arsCustKey;
	}

	public Long getArsTAXAmount() {
		return arsTAXAmount;
	}

	public void setArsTAXAmount(Long arsTAXAmount) {
		this.arsTAXAmount = arsTAXAmount;
	}

	public Integer getArsCurrencyID() {
		return arsCurrencyID;
	}

	public void setArsCurrencyID(Integer arsCurrencyID) {
		this.arsCurrencyID = arsCurrencyID;
	}

	public Integer getArsInvoiceID() {
		return arsInvoiceID;
	}

	public void setArsInvoiceID(Integer arsInvoiceID) {
		this.arsInvoiceID = arsInvoiceID;
	}

	public String getArsStatus() {
		return arsStatus;
	}

	public void setArsStatus(String arsStatus) {
		this.arsStatus = arsStatus;
	}

	public String getArsNPE() {
		return arsNPE;
	}

	public void setArsNPE(String arsNPE) {
		this.arsNPE = arsNPE;
	}

	public Long getArsBillCycleEndTime() {
		return arsBillCycleEndTime;
	}

	public void setArsBillCycleEndTime(Long arsBillCycleEndTime) {
		this.arsBillCycleEndTime = arsBillCycleEndTime;
	}

	public Long getArsInvoiceAmount() {
		return arsInvoiceAmount;
	}

	public void setArsInvoiceAmount(Long arsInvoiceAmount) {
		this.arsInvoiceAmount = arsInvoiceAmount;
	}

	public String getArsInvoiceType() {
		return arsInvoiceType;
	}

	public void setArsInvoiceType(String arsInvoiceType) {
		this.arsInvoiceType = arsInvoiceType;
	}

	public Long getArsOpenTaxAmount() {
		return arsOpenTaxAmount;
	}

	public void setArsOpenTaxAmount(Long arsOpenTaxAmount) {
		this.arsOpenTaxAmount = arsOpenTaxAmount;
	}

	public Long getArsInvoiceDate() {
		return arsInvoiceDate;
	}

	public void setArsInvoiceDate(Long arsInvoiceDate) {
		this.arsInvoiceDate = arsInvoiceDate;
	}

	public Long getArsDueDate() {
		return arsDueDate;
	}

	public void setArsDueDate(Long arsDueDate) {
		this.arsDueDate = arsDueDate;
	}

	@Override
	public String toString() {
		return "ArsInvoiceInfoArray [arsBillCycleID=" + arsBillCycleID + ", arsAdditionalProperty="
				+ arsAdditionalProperty + ", arsOpenAmount=" + arsOpenAmount + ", arsAcctKey=" + arsAcctKey
				+ ", arsInvoiceNo=" + arsInvoiceNo + ", arsSubKey=" + arsSubKey + ", arsPrimaryIdentity="
				+ arsPrimaryIdentity + ", arsTransType=" + arsTransType + ", arsBillCycleBeginTime="
				+ arsBillCycleBeginTime + ", arsCustKey=" + arsCustKey + ", arsTaxList=" 
				+ ", arsTAXAmount=" + arsTAXAmount + ", arsCurrencyID=" + arsCurrencyID + ", arsInvoiceID="
				+ arsInvoiceID + ", arsStatus=" + arsStatus + ", arsNPE=" + arsNPE + ", arsBillCycleEndTime="
				+ arsBillCycleEndTime + ", arsInvoiceAmount=" + arsInvoiceAmount + ", arsInvoiceType=" + arsInvoiceType
				+ ", arsOpenTaxAmount=" + arsOpenTaxAmount + ", arsInvoiceDate=" + arsInvoiceDate + ", arsDueDate="
				+ arsDueDate + "]";
	}
	
	
	
}
