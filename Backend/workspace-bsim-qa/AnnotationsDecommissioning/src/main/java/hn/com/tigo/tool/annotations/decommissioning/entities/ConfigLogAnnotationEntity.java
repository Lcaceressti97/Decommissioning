package hn.com.tigo.tool.annotations.decommissioning.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Laurent G. Cáceres
 */
@Table(name = "CONFIG_LOG_ANNOTATION")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigLogAnnotationEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "PROJECT", length = 50)
	private String project;

	@Column(name = "METHOD", length = 200)
	private String method;

	@Column(name = "STATUS")
	private String status;

}
