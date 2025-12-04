package hn.com.tigo.equipmentinsurance.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GeneralResponse {

	private Long code;
	private String description;
	private Object data;
	private List<GeneralError> errors = new ArrayList<>();
}
