package hn.com.tigo.equipmentaccessoriesbilling.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

import hn.com.tigo.equipmentaccessoriesbilling.models.LogsModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "MEA_LOGS")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_MEA_LOGS")
	@SequenceGenerator(name = "SQ_MEA_LOGS", sequenceName = "SQ_MEA_LOGS", allocationSize = 1)
	@Column(name = "ID", nullable = false, updatable = false)
	private Long id;

	@Column(name = "CREATED", nullable = false)
	private LocalDateTime created;

	@Column(name = "TYPE_ERROR")
	private Long typeError;

	@Column(name = "MESSAGE", length = 1000)
	private String message;

	@Column(name = "REFERENCE")
	private Long reference;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "SRT")
	private String srt;

	@Column(name = "URL", length = 200)
	private String url;

	@Column(name = "USER_CREATE", length = 50)
	private String userCreate;

	@Column(name = "EXECUTION_TIME")
	private Long executionTime;

	public LogsModel entityToModel() {
		LogsModel model = new LogsModel();
		model.setId(this.getId());
		model.setCreated(this.getCreated());
		model.setTypeError(this.getTypeError());
		model.setMessage(this.getMessage());
		model.setReference(this.getReference());
		model.setSrt(this.getSrt());
		model.setUrl(this.getUrl());
		model.setUserCreate(this.getUserCreate());
		model.setExecutionTime(this.getExecutionTime());
		return model;
	}
}
