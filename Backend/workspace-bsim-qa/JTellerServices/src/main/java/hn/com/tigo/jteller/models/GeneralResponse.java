package hn.com.tigo.jteller.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GeneralResponse {

	private Long code;
	private String description;
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

	public List<GeneralError> getErrors() {
		return errors;
	}

	public void setErrors(List<GeneralError> errors) {
		this.errors = errors;
	}
}
