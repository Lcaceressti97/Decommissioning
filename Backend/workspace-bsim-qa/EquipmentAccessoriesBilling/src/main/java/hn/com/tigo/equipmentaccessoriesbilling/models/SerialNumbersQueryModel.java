package hn.com.tigo.equipmentaccessoriesbilling.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SerialNumbersQueryModel {

	private String result_message;
	private String result_code;
	private int count;
	private List<SubWarehouse> serial_number_list;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SubWarehouse {
		private String sub_warehouse_code;
		private int count;
		private List<SerialNumber> serial_number_list;

	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class SerialNumber {
		private String serialNumber;

	}
}
