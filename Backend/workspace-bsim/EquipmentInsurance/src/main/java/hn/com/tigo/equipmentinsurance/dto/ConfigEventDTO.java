package hn.com.tigo.equipmentinsurance.dto;

import java.util.List;

import lombok.Data;

@Data
public class ConfigEventDTO {

	/** The retries. */
	private int retries;

	/** The events. */
	private List<DetailEventDTO> events;
}
