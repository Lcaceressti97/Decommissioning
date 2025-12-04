package hn.com.tigo.equipmentaccessoriesbilling.exceptions;

public class BadRequestException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String internalMessage;
	private String moreInfo;

	public BadRequestException() {
		super();
	}

	public BadRequestException(String message) {
		super(message);
	}

	public BadRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadRequestException(String message, String internalMessage, String moreInfo) {
		super(message);
		this.internalMessage = internalMessage;
		this.moreInfo = moreInfo;
	}

	public String getInternalMessage() { return internalMessage; }
	public void setInternalMessage(String internalMessage) { this.internalMessage = internalMessage; }

	public String getMoreInfo() { return moreInfo; }
	public void setMoreInfo(String moreInfo) { this.moreInfo = moreInfo; }
}
