package hn.com.tigo.equipmentaccessoriesbilling.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockQueryModel {

	private String result_message;
	private String result_code;
	private int count;
	private List<StockList> stock_list;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class StockList {
		private String warehouse_code;
		private String warehouse_name;
		private String sub_warehouse_code;
		private String sub_warehouse_name;
		private String inventory_type;
		private String inventory_type_description;
		private String item_code;
		private String item_description;
		private String line_code;
		private String line_name;
		private int quantity;

	}

}
