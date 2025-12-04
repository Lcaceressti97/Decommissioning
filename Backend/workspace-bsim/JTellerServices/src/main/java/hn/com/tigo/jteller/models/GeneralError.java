package hn.com.tigo.jteller.models;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GeneralError {

	private String code;

	private String userMessage;


}
