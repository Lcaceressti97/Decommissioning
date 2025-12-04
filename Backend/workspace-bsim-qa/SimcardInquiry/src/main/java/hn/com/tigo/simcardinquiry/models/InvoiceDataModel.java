package hn.com.tigo.simcardinquiry.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDataModel {

	private String tipo;
	private String letra;
	private String numero;
	private String estadoFactura;
	private String anexoFacturado;
	private String fechaFactura;
	private String modelo;
}
