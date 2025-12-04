package hn.com.tigo.equipmentinsurance.dto;

import java.util.List;

public class AdditionalProperties {

	/** The queue properties. */
	private List<QueueProperties> queueProperties;

	/**
	 * Sets the queue properties.
	 *
	 * @param queueProperties the new queue properties
	 */
	public void setQueueProperties(List<QueueProperties> queueProperties) {
		this.queueProperties = queueProperties;
	}

	/**
	 * Gets the queue properties.
	 *
	 * @return the queue properties
	 */
	public List<QueueProperties> getQueueProperties() {
		return this.queueProperties;
	}
}
