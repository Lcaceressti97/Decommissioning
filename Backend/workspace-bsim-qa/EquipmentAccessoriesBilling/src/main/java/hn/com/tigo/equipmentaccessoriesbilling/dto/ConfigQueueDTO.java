package hn.com.tigo.equipmentaccessoriesbilling.dto;

import java.util.List;

import lombok.Data;

@Data
public class ConfigQueueDTO {

	/** The config queue. */
	private List<DetailQueueDTO> configQueue;
}
