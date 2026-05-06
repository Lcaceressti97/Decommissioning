package hn.com.tigo.equipmentaccessoriesbilling.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayUpFrontRequest {

    private String transType;
    private AcctAccessCode acctAccessCode;
    private String payUpfrontReason;
    private String dueDate;
    private List<PayUpfrontInfo> payUpfrontInfo;
    private List<AdditionalProperty> additionalProperty;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AcctAccessCode {
        private String accountCode;
        private String payType;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PayUpfrontInfo {
        private String chargeCode;
        private Long chargeAmt;
        private int currencyId;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AdditionalProperty {
        private String code;
        private String value;

    }


}
