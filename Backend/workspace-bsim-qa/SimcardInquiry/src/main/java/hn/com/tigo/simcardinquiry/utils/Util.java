package hn.com.tigo.simcardinquiry.utils;

import java.math.BigInteger;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import hn.com.tigo.simcardinquiry.models.GeneralError;
import hn.com.tigo.simcardinquiry.models.GeneralResponse;

public class Util {

	public GeneralResponse setSuccessResponse(Object data) {
		GeneralResponse gr = new GeneralResponse();
		gr.setCode(0L);
		gr.setDescription("Operation Successful");
		if (data != null) {
			gr.setData(data);
		}

		return gr;
	}

	public GeneralResponse setSuccessWithoutData() {
		GeneralResponse gr = new GeneralResponse();
		gr.setCode(1L);
		gr.setDescription("Operation Successful");
		return gr;
	}

	public GeneralResponse setError(Exception ex, String moreInfo) {
		GeneralResponse gr = new GeneralResponse();
		GeneralError error = new GeneralError();
		error.setCode("-1");
		error.setUserMessage(ex.getMessage());
		error.setMoreInfo(moreInfo);
		error.setInternalMessage("check the log for more information");
		gr.setCode(-1L);
		gr.setDescription("Error");
		gr.getErrors().add(error);

		return gr;
	}

	public GeneralResponse setErrorResponse(GeneralError error) {
		GeneralResponse gr = new GeneralResponse();
		gr.setCode(-1L);
		gr.setDescription("Error");
		gr.getErrors().add(error);

		return gr;
	}

	public static <T> void captureAndSetResponse(HttpServletRequest request, ResponseEntity<T> responseEntity) {
		request.setAttribute("RESPONSE_ENTITY", responseEntity);
	}

	/**
	 * Método que nos ayuda a contruir el correlativo que es la parte que lleva ya
	 * sea las IMSI ó ICCD
	 * 
	 * @param length
	 * @return
	 */
	public static String getCorrelative(String length) {
		int longitud = Integer.parseInt(length); // Convertir el string a un entero

		// Formatear el nuevo string con el número de ceros deseado
		String correlative = String.format("%0" + longitud + "d", 0).replace('0', '0');

		return correlative;
	}

	public static String buildCorrelative(String value, String length) {

		Long longitud = Long.parseLong(length);
		while (value.length() < longitud) {
			value = '0' + value;
		}

		return value;
	}

	/**
	 * Método que hace la suma del siguiente valor ya sea del IMSI ó ICCD
	 * 
	 * @param value
	 * @return
	 */
	public static String buildValueFile(String value, String add) {

		// Convertir las cadenas a BigInteger
		BigInteger originalNumero = new BigInteger(value);
		BigInteger sumandoNumero = new BigInteger(add);

		// Sumar el valor
		BigInteger resultadoNumero = originalNumero.add(sumandoNumero);

		// Convertir el resultado de nuevo a una cadena
		String resultadoCadena = resultadoNumero.toString();

		return resultadoCadena;

	}

	/**
	 * Método que conpleta el correlativo del campo ICC con Ceros
	 * 
	 * @param value
	 * @return
	 */
	public static String buildInitialIcc(Map<String, String> parameters, Long suppliersEntityId, String initialIccd) {
		String initialIcc = parameters.get("CELTEL").concat(parameters.get("CELCTRY")).concat(parameters.get("CELCONA"))
				.concat(String.valueOf(suppliersEntityId));

		if (initialIcc.length() + initialIccd.length() < 18) {
			int numZerosToAdd = 18 - (initialIcc.length() + initialIccd.length());
			StringBuilder sb = new StringBuilder(initialIccd);
			for (int i = 0; i < numZerosToAdd; i++) {
				sb.insert(0, "0");
			}
			initialIccd = sb.toString();
		}

		return initialIcc.concat(initialIccd);
	}

	public static String imsiIccdArchive(String value) {
		// Convertir la cadena a BigInteger
		BigInteger originalNumero = new BigInteger(value);

		// Sumar 1
		BigInteger resultadoNumero = originalNumero.add(BigInteger.ONE);

		// Convertir el resultado de nuevo a una cadena
		String resultadoCadena = resultadoNumero.toString();

		return resultadoCadena;
	}

}
