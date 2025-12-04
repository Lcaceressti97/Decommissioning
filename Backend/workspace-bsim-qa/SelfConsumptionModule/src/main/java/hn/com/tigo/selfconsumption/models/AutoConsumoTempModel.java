package hn.com.tigo.selfconsumption.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutoConsumoTempModel {

	private String id;

	private LocalDateTime created;

	private Long status;

	private String cycle;

	private String feeItemAmount;

	private String discountedAmount;

	private String extAttribute;

	private String productId;

	private String chargeCodeType;

	private String priIdentOfSubsc;

	private String message;

	private String dataDate;

	private String request;

	private String response;

}
