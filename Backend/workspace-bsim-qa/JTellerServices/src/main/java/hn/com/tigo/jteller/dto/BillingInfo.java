package hn.com.tigo.jteller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillingInfo {

	// Props
	private int totalDocuments;
	private String totalOpenAmount;
	private String currency;
	private List<BillingDocument> billingDocuments;

}
