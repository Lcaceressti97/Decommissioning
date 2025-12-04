package hn.com.tigo.equipmentaccessoriesbilling.dto;

import java.util.List;

import lombok.Data;

@Data
public class NotifyMessageDTO {

	/**
	 * Attribute that determine eventType.
	 */
	private String eventType;

	/**
	 * Attribute that determine source.
	 */
	private String channelId;

	/**
	 * Attribute that determine orderType.
	 */
	private String orderType;

	/**
	 * Attribute that determine productId.
	 */
	private int productId;

	/**
	 * Attribute that determine subscriberId.
	 */
	private String subscriberId;

	/**
	 * Attribute that determine date.
	 */
	private String date;

	/**
	 * Attribute that determine transactionId.
	 */
	private String transactionId;

	/**
	 * Attribute that determine additionalsParams.
	 */
	private List<AttributeValuePair> additionalsParams;
}
