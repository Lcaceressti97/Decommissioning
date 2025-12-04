package hn.com.tigo.equipmentaccessoriesbilling.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveSerialBsimModel {

	private String result_message;
	private String result_code;
	private int count;
	private List<ReservationResult> reservation_result_list;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ReservationResult {
		private String serial_number;
		private String reservation_result;
	}
}