package hn.com.tigo.equipmentblacklist.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GeneralResponseImei {

	private Long code;
	private String description;
	private String phone;
	private Object data;
	private List<GeneralError> errors = new ArrayList<>();
}
