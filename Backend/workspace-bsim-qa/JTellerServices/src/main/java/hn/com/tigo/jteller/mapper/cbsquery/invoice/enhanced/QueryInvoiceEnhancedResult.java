package hn.com.tigo.jteller.mapper.cbsquery.invoice.enhanced;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class QueryInvoiceEnhancedResult {

    @SerializedName("ars:InvoiceInfo")
    private List<ArsInvoiceInfo> arsInvoiceInfo;

    @Getter
    @SerializedName("ars:TotalRowNum")
    private Integer totalRowNum;

    public List<ArsInvoiceInfo> getArsInvoiceInfo() {
        return arsInvoiceInfo;
    }

    public void setArsInvoiceInfo(List<ArsInvoiceInfo> arsInvoiceInfo) {
        this.arsInvoiceInfo = arsInvoiceInfo;
    }

    public Integer getArsTotalRowNum() {
        return totalRowNum;
    }

    public void setArsTotalRowNum(Integer arsTotalRowNum) {
        this.totalRowNum = arsTotalRowNum;
    }


    public void setTotalRowNum(Integer totalRowNum) {
        this.totalRowNum = totalRowNum;
    }


    @Override
    public String toString() {
        return "QueryInvoiceEnhancedResult{" +
                "arsInvoiceInfo=" + arsInvoiceInfo +
                '}';
    }
}
