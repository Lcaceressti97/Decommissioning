package hn.com.tigo.equipmentinsurance.exceptions;

import lombok.Getter;

@Getter
public class UnAuthorizedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	private String internalMessage;
	private String moreInfo;

	public UnAuthorizedException() {
		super();
	}

	public UnAuthorizedException(String message) {
		super(message);
	}

	public UnAuthorizedException(String message, String internalMessage, String moreInfo) {
		this.message = message;
		this.moreInfo = moreInfo;
		this.internalMessage = internalMessage;
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
