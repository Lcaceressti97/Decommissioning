package hn.com.tigo.tool.annotations.decommissioning.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParametersModel {

	private long idApplication;

	private String name;

	private String value;

	private String description;

	private LocalDateTime createdDate;
}
