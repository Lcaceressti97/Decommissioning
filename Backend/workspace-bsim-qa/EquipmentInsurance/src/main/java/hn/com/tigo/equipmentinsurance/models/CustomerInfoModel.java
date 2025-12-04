package hn.com.tigo.equipmentinsurance.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfoModel {

	private String customerName;

	private String customerAddress;

	private String customerRtn;

	private String primaryIdentity;

	private String customerId;
}
