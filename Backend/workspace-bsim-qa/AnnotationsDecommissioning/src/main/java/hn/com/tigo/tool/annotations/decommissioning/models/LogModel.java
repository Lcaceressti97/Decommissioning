package hn.com.tigo.tool.annotations.decommissioning.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogModel {

	private Long idApp;

	private String dataSource;

	private String startDate;

	private String endDate;

	private String request;

	private String response;

	private String method;

	private String apiMethod;

	private String httpResponseCode;

	private String uri;
}
