package hn.com.tigo.tool.annotations.decommissioning.models;

import java.util.ArrayList;
import java.util.List;

import lombok.ToString;

@ToString
public class GeneralResponse {

	private Long code;
	private String description;
	private Object data;
	private List<GeneralError> errors = new ArrayList<>();

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public List<GeneralError> getErrors() {
		return errors;
	}

	public void setErrors(List<GeneralError> errors) {
		this.errors = errors;
	}

}
