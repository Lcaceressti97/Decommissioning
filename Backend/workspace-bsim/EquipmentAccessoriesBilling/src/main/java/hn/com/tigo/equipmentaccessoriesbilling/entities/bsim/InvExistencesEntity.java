package hn.com.tigo.equipmentaccessoriesbilling.entities.bsim;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import hn.com.tigo.equipmentaccessoriesbilling.models.bsim.InvExistencesModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "INV_EXISTENCES", schema = "STCINVENTORY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvExistencesEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false, updatable = false)
	private Long id;

	@Column(name = "VERSION")
	private BigDecimal version;

	@Column(name = "TYPE_INVENTORY_ID")
	private BigDecimal typeInventoryId;

	@Column(name = "MODEL_OR_ARTICLE_ID")
	private BigDecimal modelOrArticleId;

	@Column(name = "WAREHOUSE_ID")
	private BigDecimal warehouseId;

	@Column(name = "TOTAL_COST_ML")
	private BigDecimal totalCostMil;

	@Column(name = "QUANTITY_EXISTENCE")
	private BigDecimal quantityExistence;

	@Column(name = "QUANTITY_RESERVED")
	private BigDecimal quantityReserved;

	@Column(name = "WARRANTY_EXPIRATION_DATE")
	private Timestamp warrantyExpirationDate;

	@Column(name = "PRIMARY_IDENTIFICATION", length = 40)
	private String primaryIdentification;

	@Column(name = "SECONDARY_IDENTIFICATION", length = 40)
	private String secondaryIdentification;

	@Column(name = "SERVICE_IDENTIFICATION", length = 40)
	private String serviceIdentification;

	@Column(name = "LOCALIZATION", length = 40)
	private String localization;

	@Column(name = "NUMBER_LOT", length = 40)
	private String numberLot;

	@Column(name = "UNIT_COST")
	private BigDecimal unitCost;

	@Column(name = "LEGACY_CODE", length = 20)
	private String legacyCode;

	@Column(name = "SUB_WAREHOUSE_ID")
	private BigDecimal subWarehouseId;

	@Column(name = "LAST_TRANSACTION_ID")
	private BigDecimal lastTransactionId;

	@Column(name = "PREVIOUS_TRANSACTION_ID")
	private BigDecimal reviousTransactionId;

	@Column(name = "AUTHORIZED", length = 1)
	private String authorized;

	@Column(name = "LAST_TRANSFER_ID")
	private BigDecimal lastTransferId;

	@Column(name = "PREVIOUS_TRANSFER_ID")
	private BigDecimal previousTransferId;

	@Column(name = "LAST_RECEIPT_DATE")
	private Timestamp lastReceiptDate;

	@Column(name = "PREVIOUS_RECEIPT_DATE")
	private Timestamp previousReceiptDate;

	@Column(name = "BOX_NUMBER")
	private BigDecimal boxNumber;

	@Column(name = "LOAD_DATE")
	private Timestamp loadDate;

	@Column(name = "LOAD_COST")
	private BigDecimal loadCost;
	
	private String model;
	
	private String description;

	private String code;
	
	private BigDecimal equipmentLineId;


	public InvExistencesModel entityToModel() {
		InvExistencesModel model = new InvExistencesModel();

		model.setId(this.getId());
		model.setVersion(this.getVersion());
		model.setTypeInventoryId(this.getTypeInventoryId());
		model.setModelOrArticuleId(this.getModelOrArticleId());
		model.setTotalCostMil(this.getTotalCostMil());
		model.setQuantityExistence(this.getQuantityExistence());
		model.setQuantityReserved(this.getQuantityReserved());
		model.setWarrantyExpirationDate(this.getWarrantyExpirationDate());
		model.setPrimaryIdentification(this.getPrimaryIdentification());
		model.setSecondaryIdentification(this.getSecondaryIdentification());
		model.setServiceIdentification(this.getServiceIdentification());
		model.setLocalization(this.getLocalization());
		model.setNumberLot(this.getNumberLot());
		model.setUnitCost(this.getUnitCost());
		model.setLegacyCode(this.getLegacyCode());
		model.setSubWarehouseId(this.getSubWarehouseId());
		model.setLastTransactionId(this.getLastTransactionId());
		model.setPreviousTransactionId(this.getReviousTransactionId());
		model.setAuthorized(this.getAuthorized());
		model.setLastTransferId(this.getLastTransferId());
		model.setPreviousTransferId(this.getPreviousTransferId());
		model.setLastReceiptDate(this.getLastReceiptDate());
		model.setPreviousReceiptDate(this.getPreviousReceiptDate());
		model.setBoxNumber(this.getBoxNumber());
		model.setLoadDate(this.getLoadDate());
		model.setLoadCost(this.getLoadCost());

		return model;
	}
}
