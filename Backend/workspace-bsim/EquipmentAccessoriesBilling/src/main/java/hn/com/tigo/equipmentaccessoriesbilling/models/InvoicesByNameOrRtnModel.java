package hn.com.tigo.equipmentaccessoriesbilling.models;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class InvoicesByNameOrRtnModel {
	
	private BigDecimal id;
	private String invoiceType;
	private BigDecimal status;
	private BigDecimal exonerationStatus;
	private String customer;
	private String seller;
	private String primaryIdentity;
	private Timestamp created;
}
