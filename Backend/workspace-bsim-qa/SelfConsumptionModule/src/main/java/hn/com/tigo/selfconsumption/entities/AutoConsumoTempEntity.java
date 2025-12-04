package hn.com.tigo.selfconsumption.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import hn.com.tigo.selfconsumption.models.AutoConsumoTempModel;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "AUTO_CONSUMO_TEMP")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutoConsumoTempEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private String id;

	@Column(name = "CREATED", nullable = false)
	private LocalDateTime created;

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "CYCLE", length = 8)
	private String cycle;

	@Column(name = "FEE_ITEM_AMOUNT", length = 50)
	private String feeItemAmount;

	@Column(name = "DISCOUNTED_AMOUNT", length = 50)
	private String discountedAmount;

	@Column(name = "EXT_ATTRIBUTE", length = 200)
	private String extAttribute;

	@Column(name = "PRODUCT_ID", length = 20)
	private String productId;

	@Column(name = "CHARGE_CODE_TYPE", length = 20)
	private String chargeCodeType;

	@Column(name = "PRI_IDENT_OF_SUBSC", length = 20)
	private String priIdentOfSubsc;

	@Column(name = "MESSAGE", length = 1000)
	private String message;

	@Column(name = "DATA_DATE", length = 20)
	private String dataDate;

	@Lob
	@Column(name = "REQUEST")
	private String request;

	@Lob
	@Column(name = "RESPONSE")
	private String response;

	public AutoConsumoTempModel entityToModel() {
		AutoConsumoTempModel model = new AutoConsumoTempModel();

		model.setId(this.getId());
		model.setCreated(this.getCreated());
		model.setStatus(this.getStatus());
		model.setCycle(this.getCycle());
		model.setFeeItemAmount(this.getFeeItemAmount());
		model.setDiscountedAmount(this.getDiscountedAmount());
		model.setExtAttribute(this.getExtAttribute());
		model.setProductId(this.getProductId());
		model.setChargeCodeType(this.getChargeCodeType());
		model.setPriIdentOfSubsc(this.getPriIdentOfSubsc());
		model.setMessage(this.getMessage());
		model.setDataDate(this.getDataDate());
		model.setRequest(this.getRequest());
		model.setResponse(this.getResponse());
		return model;
	}
}
