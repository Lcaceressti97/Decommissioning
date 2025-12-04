package hn.com.tigo.equipmentaccessoriesbilling.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnloadReservedStockBsimModel {

	private String result_message;
	private String result_code;
	private Long transaction_number;
	private int count;
	private List<LoadResultList> unload_result_list;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class LoadResultList {
		private String serial_number;
		private String transaction_result;
	}
	
}
