package hn.com.tigo.simcardinquiry.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import hn.com.tigo.simcardinquiry.models.SimcardOrderControlModel;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "SIMCARD_ORDER_CONTROL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimcardOrderControlEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false, updatable = false)
	private Long id;

	@Column(name = "ID_SUPPLIER")
	private Long idSupplier;

	@Column(name = "SUPPLIER_NAME", length = 100)
	private String supplierName;

	@Column(name = "NO_ORDER")
	private Long noOrder;

	@Column(name = "USER_NAME", length = 50)
	private String userName;

	@Column(name = "CUSTOMER_NAME", length = 50)
	private String customerName;

	@Column(name = "INITIAL_IMSI")
	private String initialImsi;

	@Column(name = "INITIAL_ICCD")
	private String initialIccd;

	@Column(name = "ORDER_QUANTITY")
	private Long orderQuantity;

	@Column(name = "FILE_NAME")
	private String fileName;

	@Lob
	@Column(name = "ORDER_FILE")
	private String orderFile;

	@Lob
	@Column(name = "ORDER_DETAIL_FILE")
	private String orderDetailFile;

	@Column(name = "CREATED_DATE")
	private LocalDateTime createdDate;

	@Column(name = "CUSTOMER")
	private String customer;

	@Column(name = "HLR")
	private String hlr;

	@Column(name = "BATCH")
	private String batch;

	@Column(name = "KEY")
	private String key;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "ART")
	private String art;

	@Column(name = "GRAPHIC")
	private String graphic;

	@Column(name = "MODEL")
	private String model;

	@Column(name = "VERSION_SIZE")
	private String versionSize;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "EMAIL_SEND")
	private String email;

	public SimcardOrderControlModel entityToModel() {
		SimcardOrderControlModel model = new SimcardOrderControlModel();
		model.setId(this.getId());
		model.setIdSupplier(this.getIdSupplier());
		model.setSupplierName(this.getSupplierName());
		model.setNoOrder(this.getNoOrder());
		model.setUserName(this.getUserName());
		model.setCustomerName(this.getCustomerName());
		model.setInitialImsi(this.getInitialImsi());
		model.setInitialIccd(this.getInitialIccd());
		model.setOrderQuantity(this.getOrderQuantity());
		model.setFileName(this.getFileName());
		model.setCreatedDate(this.getCreatedDate());
		model.setCustomer(this.getCustomer());
		model.setHlr(this.getHlr());
		model.setBatch(this.getBatch());
		model.setKey(this.getKey());
		model.setType(this.getType());
		model.setArt(this.getArt());
		model.setGraphic(this.getGraphic());
		model.setModel(this.getModel());
		model.setVersionSize(this.getVersionSize());
		model.setStatus(this.getStatus());
		model.setEmail(this.getEmail());
		return model;
	}

	@Override
	public String toString() {
		return "SimcardOrderControlEntity [id=" + id + ", noOrder=" + noOrder + ", initialImsi=" + initialImsi
				+ ", initialIccd=" + initialIccd + ", batch=" + batch + "]";
	}

}
