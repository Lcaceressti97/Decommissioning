package hn.com.tigo.inquiriesamsysnavega.models;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NavegaBalancesModel {

	private Long id;

	private String customerName;

	private String customerNo;

	private String customerCode;

	private String product;

	private String ebsAccount;

	private Long idOrganization;

	private String organizationName;

	private String currency;

	private Double balance;

	private Date closingDate;

}
