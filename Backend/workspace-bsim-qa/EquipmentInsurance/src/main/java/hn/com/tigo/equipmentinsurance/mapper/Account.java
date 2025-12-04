package hn.com.tigo.equipmentinsurance.mapper;

import com.google.gson.annotations.SerializedName;

public class Account {
    @SerializedName("AcctKey")
    private Long acctKey;
    @SerializedName("AcctInfo")
    private AcctInfo acctInfo;

    public Long getAcctKey() {
        return acctKey;
    }

    public void setAcctKey(Long acctKey) {
        this.acctKey = acctKey;
    }

    public AcctInfo getAcctInfo() {
        return acctInfo;
    }

    public void setAcctInfo(AcctInfo acctInfo) {
        this.acctInfo = acctInfo;
    }

    @Override
    public String toString() {
        return "Account [acctKey=" + acctKey + ", acctInfo=" + acctInfo + "]";
    }

}
