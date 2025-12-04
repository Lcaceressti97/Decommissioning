package hn.com.tigo.tool.annotations.decommissioning.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import hn.com.tigo.tool.annotations.decommissioning.models.LogModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Laurent G. Cáceres
 */

@Table(name = "LOG_ANNOTATION")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_APP", insertable = false, updatable = false)
	private Long idApp;

	@Column(name = "DATASOURCE")
	private String dataSource;

	@Column(name = "START_DATE")
	private String startDate;

	@Column(name = "END_DATE")
	private String endDate;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "REQUEST")
	private String request;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "RESPONSE")
	private String response;

	@Column(name = "METHOD")
	private String method;

	@Column(name = "API_METHOD")
	private String apiMethod;

	@Column(name = "HTTP_RESPONSE_CODE")
	private String httpResponseCode;

	@Column(name = "URI")
	private String uri;

	public LogModel entityToModel() {
		LogModel model = new LogModel();
		model.setIdApp(this.getIdApp());
		model.setDataSource(this.getDataSource());
		model.setStartDate(this.getStartDate());
		model.setEndDate(this.getEndDate());
		model.setRequest(this.getRequest());
		model.setResponse(this.getResponse());
		model.setMethod(this.getMethod());
		model.setApiMethod(this.getApiMethod());
		model.setHttpResponseCode(this.getHttpResponseCode());
		model.setUri(this.getUri());
		return model;
	}

}
