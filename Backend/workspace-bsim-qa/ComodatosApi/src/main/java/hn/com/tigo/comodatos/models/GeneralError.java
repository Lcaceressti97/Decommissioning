package hn.com.tigo.comodatos.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.ToString;

@ToString
public class GeneralError {

	private String code;
	private String userMessage;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String moreInfo;
	private String internalMessage;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	public String getMoreInfo() {
		return moreInfo;
	}

	public void setMoreInfo(String moreInfo) {
		this.moreInfo = moreInfo;
	}

	public String getInternalMessage() {
		return internalMessage;
	}

	public void setInternalMessage(String internalMessage) {
		this.internalMessage = internalMessage;
	}

}
