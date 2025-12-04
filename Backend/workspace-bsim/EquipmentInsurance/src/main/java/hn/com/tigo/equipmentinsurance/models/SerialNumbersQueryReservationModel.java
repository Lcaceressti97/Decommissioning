package hn.com.tigo.equipmentinsurance.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SerialNumbersQueryReservationModel {
	
	private String result_message;
	private String result_code;
	private int count;
	private String stock_list;
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
		private String serial_number;
		private String reservation_result;
	}
}
