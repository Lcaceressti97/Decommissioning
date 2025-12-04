package hn.com.tigo.jteller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import hn.com.tigo.jteller.dto.BillingInfo;
import hn.com.tigo.jteller.dto.InfoCliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"responseCode","responseDescription","InfoCliente","billingInfo"})
public class ClientInfoPreBillResponse {

	// Props
	private Integer responseCode;
	private String responseDescription;
	
	@JsonProperty("InfoCliente")
	private InfoCliente infoCliente;
	private BillingInfo billingInfo;

}
