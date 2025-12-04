package hn.com.tigo.equipmentaccessoriesbilling.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceEquipmentAccessoriesModel {

	private Long id;

	private String invoiceNo;

	private String invoiceType;

	private Long invoiceStatus;

	private String invoiceStatusName;

	private String billingAccount;

	private String subscriber;

	private String custcode;

	private String emailaddr;

	private String company;

	private String cai;

	private String customerName;

	private String customerType;

	private String documentNo;

	private String diplomaticCardNo;

	private String correlativeOrdenExemptNo;

	private String correlativeCertificateExoNo;

	private Long exonerationStatus;

	private String exonerationStatusName;

	private String xml;

	private String path;

	private String address;

	private String warehouse;

	private String agency;

	private String transactionUser;

	private String chargeLocal;

	private String chargeUsd;

	private String exchangeRate;

	private String tax;

	private String discount;

	private LocalDateTime created;

	private String createdBy;

}
