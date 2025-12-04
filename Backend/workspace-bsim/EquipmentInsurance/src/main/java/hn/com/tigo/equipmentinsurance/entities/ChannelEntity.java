package hn.com.tigo.equipmentinsurance.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.equipmentinsurance.models.ChannelModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "MEA_CHANNELS")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChannelEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "NAME", length = 100)
	private String name;

	@Column(name = "DESCRIPTION", length = 150)
	private String description;

	@Column(name = "PAYUPFRONTNUMBER")
	private Long payUpFrontNumber;

	@Column(name = "NONFISCALNOTE")
	private Long nonFiscalNote;

	@Column(name = "RESERVESERIALNUMBER")
	private Long reserveSerialNumber;

	@Column(name = "RELEASESERIALNUMBER")
	private Long releaseSerialNumber;

	@Column(name = "INVENTORYDOWNLOAD")
	private Long inventoryDownload;

	@Column(name = "GENERATETRAMA")
	private Long generateTrama;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "CREATED", nullable = false)
	private LocalDateTime created;

	public ChannelModel entityToModel() {
		ChannelModel model = new ChannelModel();

		model.setId(this.getId());
		model.setName(this.getName());
		model.setDescription(this.getDescription());
		model.setPayUpFrontNumber(this.getPayUpFrontNumber());
		model.setNonFiscalNote(this.getNonFiscalNote());
		model.setReserveSerialNumber(this.getReserveSerialNumber());
		model.setReleaseSerialNumber(this.getReleaseSerialNumber());
		model.setInventoryDownload(this.getInventoryDownload());
		model.setGenerateTrama(this.getGenerateTrama());
		model.setStatus(this.getStatus());
		model.setCreated(this.getCreated());
		return model;
	}

}
