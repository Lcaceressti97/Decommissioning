package hn.com.tigo.equipmentinsurance.models;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GeneralError {

	private String code;

	private String userMessage;

	private String moreInfo;

	private String internalMessage;

}
