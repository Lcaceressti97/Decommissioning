package hn.com.tigo.equipmentinsurance.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalInvoiceModel {

	private Double totalSubtotal;
	private Double exchangeRate;
	private Double taxPercentage;
	private Double totalTax;
	private Double discountPercentage;
	private Double totalDiscount;
	private Double totalAmount;

}
