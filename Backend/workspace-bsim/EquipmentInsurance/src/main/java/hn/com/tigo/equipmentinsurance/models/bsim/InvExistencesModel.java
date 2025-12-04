package hn.com.tigo.equipmentinsurance.models.bsim;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvExistencesModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private BigDecimal version;

	private BigDecimal typeInventoryId;

	private BigDecimal modelOrArticuleId;

	private String model;

	private BigDecimal warehouseId;

	private BigDecimal totalCostMil;

	private BigDecimal quantityExistence;

	private BigDecimal quantityReserved;

	private Timestamp warrantyExpirationDate;

	private String primaryIdentification;

	private String secondaryIdentification;

	private String serviceIdentification;

	private String localization;

	private String numberLot;

	private BigDecimal unitCost;

	private String legacyCode;

	private BigDecimal subWarehouseId;

	private BigDecimal lastTransactionId;

	private BigDecimal previousTransactionId;

	private String authorized;

	private BigDecimal lastTransferId;

	private BigDecimal previousTransferId;

	private Timestamp lastReceiptDate;

	private Timestamp previousReceiptDate;

	private BigDecimal boxNumber;

	private Timestamp loadDate;

	private BigDecimal loadCost;

}