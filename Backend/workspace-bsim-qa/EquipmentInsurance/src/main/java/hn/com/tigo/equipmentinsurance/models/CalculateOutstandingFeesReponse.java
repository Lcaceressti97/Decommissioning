package hn.com.tigo.equipmentinsurance.models;

import lombok.Data;

@Data
public class CalculateOutstandingFeesReponse {

	private String equipmentModel;

	private String esn;

	private Double totalPeriod1;

	private Double totalPeriod2;

	private Double totalPeriod3;

	private Double totalPeriods;
}
