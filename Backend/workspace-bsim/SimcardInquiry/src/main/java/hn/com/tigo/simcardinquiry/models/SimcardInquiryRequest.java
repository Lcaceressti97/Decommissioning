package hn.com.tigo.simcardinquiry.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimcardInquiryRequest {

	private BasicRequestModel basicRequest;
	private String simcard;
	private ParametersModel parameters;
	private String imsi;

}
