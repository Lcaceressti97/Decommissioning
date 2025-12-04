package hn.com.tigo.jteller.utils;

import hn.com.tigo.jteller.exceptions.BadRequestException;

public class ValidateParam {

	public static void type(String type) {

		// Validamos que no sea null y que no venga con espacio ni vacio
		if (type == null || type.trim().isEmpty()) {
			throw new BadRequestException("El parámetro [type] debe estar declaro y no puede estar vacio");
		}

		final boolean validatePrimeryIdentity = type.equals("primaryIdentity");
		final boolean validateAccountCode = type.equals("accountCode");

		if (validatePrimeryIdentity == false) {

			if (validateAccountCode == false) {
				throw new BadRequestException("Value Invalid");
			}
		}

	}

	public static void typeBill(String type) {

		// Validamos que no sea null y que no venga con espacio ni vacio
		if (type == null || type.trim().isEmpty()) {
			throw new BadRequestException("El parámetro [type] debe estar declaro y no puede estar vacio");
		}

		final boolean validatePreBill = type.equals("PreBill");

		if (validatePreBill == false) {
			throw new BadRequestException("Value Invalid");
		}

	}

	public static void value(String value) {
		if (value == null || value.trim().isEmpty()) {
			throw new BadRequestException("El parámetro [value] debe estar declaro y no puede estar vacio");
		}

		try {
			long edadNumerica = Long.parseLong(value);
			if (edadNumerica < 0) {
				throw new BadRequestException("El parámetro [value] debe de ser un número");
			}
		} catch (NumberFormatException e) {
			throw new BadRequestException("El parámetro [value] debe de ser un número");
		}

	}
}
