package hn.com.tigo.equipmentinsurance.models;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CalculateOutstandingFeesRequest {

	private String equipmentModel;

	private String esn;

	private LocalDateTime startDate;
}
