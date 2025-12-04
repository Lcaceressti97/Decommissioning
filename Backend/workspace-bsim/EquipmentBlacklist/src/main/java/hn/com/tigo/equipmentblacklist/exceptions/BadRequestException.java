package hn.com.tigo.equipmentblacklist.exceptions;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	private String internalMessage;
	private String moreInfo;

	public BadRequestException() {
		super();
	}

	public BadRequestException(String message) {
		super(message);
	}

	public BadRequestException(String message, String internalMessage, String moreInfo) {
		this.message = message;
		this.internalMessage = internalMessage;
		this.moreInfo = moreInfo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getInternalMessage() {
		return internalMessage;
	}

	public void setInternalMessage(String internalMessage) {
		this.internalMessage = internalMessage;
	}

	public String getMoreInfo() {
		return moreInfo;
	}

	public void setMoreInfo(String moreInfo) {
		this.moreInfo = moreInfo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
