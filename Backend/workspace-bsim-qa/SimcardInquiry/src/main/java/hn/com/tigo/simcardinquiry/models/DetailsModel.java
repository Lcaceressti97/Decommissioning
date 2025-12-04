package hn.com.tigo.simcardinquiry.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailsModel {

	private String imsi;
	private String estado;
	private String ki;
	private String pin;
	private String puk1;
	private String puk2;
	private String icc;
	private String anexoAsociado;
	private InvoiceDataModel datosFactura;
	private ShieldingDataModel datosBlindaje;
	private ParametersModel parameters;
}
