package hn.com.tigo.equipmentaccessoriesbilling.models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelVoucherRequest {

	private Long idInvoice;
	private String user;
}
