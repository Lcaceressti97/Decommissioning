package hn.com.tigo.equipmentaccessoriesbilling.dto;

import lombok.Data;

@Data
public class DetailQueueDTO {

	/** Attribute that determine queue end point or address. */
	private String queueipaddress;

	/** Attribute that determine queue authentication user. */
	private String queueuser;

	/** Attribute that determine queue password. */
	private String queuepassword;

	/** Attribute that determine queue library name. */
	private String queuelibname;

	/** Attribute that determine queue name. */
	private String queuename;

	/** Attribute that determine queue split character. */
	private String queuesplit;
}
