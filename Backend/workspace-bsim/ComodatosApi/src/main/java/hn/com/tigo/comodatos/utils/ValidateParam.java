package hn.com.tigo.comodatos.utils;

import javax.ws.rs.BadRequestException;

public class ValidateParam {

	/**
	 * Método estatico encargado de validar el campo type, que no venga vacío y
	 * debe de ser subscriber || billingAccount
	 * 
	 * @param type
	 */
	public static void type(String type) {
		// Validamos que no sea null y que no venga con espacio ni vacio
		if (type == null || type.trim().isEmpty()) {
			throw new BadRequestException("The field [type] cannot be empty");
		}

		final boolean validatePrimeryIdentity = type.equals("subscriber");
		final boolean validateAccountCode = type.equals("billingAccount");

		/**
		 * Validamos si son los valores acordados para el type
		 * 
		 */
		if (validatePrimeryIdentity == false) {

			if (validateAccountCode == false) {
				throw new BadRequestException("The value of [type] is not valid");
			}
		}
	}
	
	public static void value(String value, String type) {
		// Validamos que no sea null y que no venga con espacio ni vacio
		if (value == null || value.trim().isEmpty()) {
			throw new BadRequestException(String.format("The field [%s] cannot be empty",type));
		}
	}
	
	
}
