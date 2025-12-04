package hn.com.tigo.equipmentaccessoriesbilling.models;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateModel {

	private int idExchange;
	
	private BigDecimal salePrice;
	
	BigDecimal purchasePrice;
	
}
