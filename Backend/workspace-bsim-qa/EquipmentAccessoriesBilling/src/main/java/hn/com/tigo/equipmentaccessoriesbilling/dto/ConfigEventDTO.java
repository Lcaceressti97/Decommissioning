package hn.com.tigo.equipmentaccessoriesbilling.dto;

import java.util.List;

import lombok.Data;

@Data
public class ConfigEventDTO {

	/** The retries. */
	private int retries;

	/** The events. */
	private List<DetailEventDTO> events;

}
