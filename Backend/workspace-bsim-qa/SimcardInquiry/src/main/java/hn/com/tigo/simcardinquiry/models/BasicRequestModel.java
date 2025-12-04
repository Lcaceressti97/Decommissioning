package hn.com.tigo.simcardinquiry.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicRequestModel {

	private String externalApplicationID;
	private String externalTransactionID;
	private String utiReference;
}
