package hn.com.tigo.jteller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonPropertyOrder({"exchangeRate","invoiceNo","invoicePre","openAmount","invoiceAmount"})
public class BillingDocument {
	private String exchangeRate;
	
	@JsonProperty("invoicePre")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String invoicePre;
	
	@JsonProperty("invoiceNo")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String invoiceNo;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long invoiceId;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String openAmount;
	private String invoiceAmount;
	private String taxAmount;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String openTaxAmount;
	private String transType;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String invoiceDate;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String dueDate;

}
