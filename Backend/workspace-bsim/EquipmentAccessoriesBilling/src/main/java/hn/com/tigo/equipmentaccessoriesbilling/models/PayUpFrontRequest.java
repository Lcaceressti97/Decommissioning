package hn.com.tigo.equipmentaccessoriesbilling.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayUpFrontRequest {

	private String payUpfrontSerialNo;
	private String transType;
	private AcctAccessCode acctAccessCode;
	private List<PayUpfrontInfo> payUpfrontInfo;
	private String payUpfrontReason;
	private String dueDate;

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

}
