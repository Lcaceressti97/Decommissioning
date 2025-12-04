package hn.com.tigo.inquiriesamsysnavega.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import hn.com.tigo.inquiriesamsysnavega.models.LogsModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "CN_LOGS")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "CREATED", nullable = false)
	private LocalDateTime created;

	@NotNull(message = "TYPE_ERROR cannot be null")
	@Column(name = "TYPE_ERROR")
	private Long typeError;

	@NotBlank(message = "MESSAGE cannot be blank")
	@Column(name = "MESSAGE", length = 1000)
	private String message;

	// @NotBlank(message = "REFERENCE cannot be blank")
	@Column(name = "REFERENCE")
	private Long reference;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "SRT")
	private String srt;

	@NotBlank(message = "URL cannot be blank")
	@Column(name = "URL", length = 200)
	private String url;

	public LogsModel entityToModel() {
		LogsModel model = new LogsModel();
		model.setId(this.getId());
		model.setCreated(this.getCreated());
		model.setTypeError(this.getTypeError());
		model.setMessage(this.getMessage());
		model.setReference(this.getReference());
		model.setSrt(this.getSrt());
		model.setUrl(this.getUrl());
		return model;
	}
}
