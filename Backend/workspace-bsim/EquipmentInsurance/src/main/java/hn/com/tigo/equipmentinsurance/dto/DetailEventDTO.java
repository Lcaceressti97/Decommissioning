package hn.com.tigo.equipmentinsurance.dto;

import java.util.List;

import lombok.Data;

@Data
public class DetailEventDTO {

	/** The name. */
	private String name;

	/** The default product. */
	private String defaultProduct;

	/** The comment. */
	private String comment;

	/** The channel id. */
	private int channelId;

	/** The default order. */
	private String defaultOrder;

	/** The endpoint. */
	private String endpoint;

	/** The subevents. */
	private List<DetailSubEventDTO> subevents;
}
