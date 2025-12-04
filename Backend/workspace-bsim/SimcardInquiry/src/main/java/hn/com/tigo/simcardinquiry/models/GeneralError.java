package hn.com.tigo.simcardinquiry.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GeneralError {

	private String code;

	private String userMessage;

	private String moreInfo;

	private String internalMessage;

}
