package hn.com.tigo.jteller.service;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import hn.com.tigo.jteller.mapper.cbsquery.customer.info.AddressInfo;
import hn.com.tigo.jteller.mapper.cbsquery.customer.info.AddressInfoOnlyCode;
import hn.com.tigo.jteller.mapper.cbsquery.customer.info.AddressInfoOnlyOne;

/**
 * Servicio encargado de mapear la dirección
 * 
 */
public class AddressService {
	public static String getAddress(Object address, Gson gson) {
		String addressReturn = "";
		// Convertir el objecto en un string para validar si contiene un objeto o un
		// array
		String objectString = address.toString();
		String objectJson = objectString;

		if (objectString.contains("[")) {

			// Convertir la variable Object en una lista de tipo Object
			List<Object> list = (List<Object>) address;
			String itemStringOne = null;
			String itemStringTwo = null;
			int i = 0;

			// 1. Usando un bucle for each obtenemos los valores si es un array
			for (Object item : list) {
				if (i == 0) {
					itemStringOne = item.toString();
				} else {
					itemStringTwo = item.toString();
				}

				i++;

			}

			// Verificamos si trabajamos con el primer arreglo
			// o el segundo
			if (itemStringTwo.contains("OTROS")) {

				if (itemStringOne.contains("PostCode")) {
					// Reemplaza el formato de la cadena
					String output = itemStringOne.replaceAll(
							"\\{([^=]+)=([^,]+),\\s+([^=]+)=([^,]+),\\s+([^=]+)=([^}]+)}",
							"{\"$1\":$2,\"$3\":\"$4\",\"$5\":\"$6\"}");
					AddressInfoOnlyCode addresInfoOnlyCode = gson.fromJson(output, AddressInfoOnlyCode.class);
					addressReturn = addresInfoOnlyCode.getAddres1();

				} else {
					// Reemplaza el formato de la cadena
					String output = itemStringTwo.replaceAll("(\\w+)=(\\w+|.*?)(?=,\\s+\\w+=|$)", "\"$1\":\"$2\"");

					// Reemplaza los espacios en blanco después de las comas
					String output2 = output.replaceAll(",\\s+", ",");

					String output3 = output2.substring(0, output2.length() - 2);
					output3 += "\"";
					output3 += "}";
					
					AddressInfo addresInfoOnlyCode = null;
					try {
					 addresInfoOnlyCode = gson.fromJson(output3, AddressInfo.class);
					 addressReturn = addresInfoOnlyCode.getAddress2() + ", " + addresInfoOnlyCode.getAddress3() + ", "
							+ addresInfoOnlyCode.getAddress4() + ", " + addresInfoOnlyCode.getAddress1();
					} catch (Exception e) {

						addressReturn = output3;
					}
				}

			} else {
				// Reemplaza el formato de la cadena
				String output = itemStringTwo.replaceAll("(\\w+)=(\\w+|.*?)(?=,\\s+\\w+=|$)", "\"$1\":\"$2\"");

				// Reemplaza los espacios en blanco después de las comas
				String output2 = output.replaceAll(",\\s+", ",");

				String output3 = output2.substring(0, output2.length() - 2);
				output3 += "\"";
				output3 += "}";
				
				AddressInfo addresInfoOnlyCode = null;
				try {
				 addresInfoOnlyCode = gson.fromJson(output3, AddressInfo.class);
				 addressReturn = addresInfoOnlyCode.getAddress2() + ", " + addresInfoOnlyCode.getAddress3() + ", "
						+ addresInfoOnlyCode.getAddress4() + ", " + addresInfoOnlyCode.getAddress1();
				} catch (Exception e) {

					addressReturn = output2;
				}
			}

		} else {

			if (objectString.contains("Address1") && objectString.contains("Address2")
					&& objectString.contains("Address3") && objectString.contains("Address4")) {
				// Reemplazar el formato de la cadena utilizando replaceAll
				//System.out.println(objectJson);
				objectJson = objectJson.replaceAll("(, Address)", ";$1");
				objectJson = objectJson.replace(",", "");
				//System.out.println(objectJson);
				String output = objectJson.replaceAll("(\\w+)=([^;]+)", "\"$1\":\"$2\"");
				String output2 = output.substring(0, output.length() - 2);
				output2 += "\"";
				output2 += "}"; 
				
				AddressInfo addresInfoOnlyCode = null;
				try {
					addresInfoOnlyCode = gson.fromJson(output2, AddressInfo.class);
					addressReturn = addresInfoOnlyCode.getAddress2() + ", " + addresInfoOnlyCode.getAddress3() + ", "
							+ addresInfoOnlyCode.getAddress4() + ", " + addresInfoOnlyCode.getAddress1();
				} catch (Exception e) {

					addressReturn = output2;
				}

				//System.out.println(output2);// objectJson
			} else {
				if (objectString.contains("Address1") && objectString.contains("AddressKey")) {
					
					objectJson = objectJson.replaceAll("(, Address)", ";$1");
					objectJson = objectJson.replace(",", "");
					//System.out.println(objectJson);
					String output = objectJson.replaceAll("(\\w+)=([^;]+)", "\"$1\":\"$2\"");
					String output2 = output.substring(0, output.length() - 2);
					output2 += "\"";
					output2 += "}"; 

					AddressInfoOnlyOne addressOnlyOne = null;
					try {
					 addressOnlyOne = gson.fromJson(output2, AddressInfoOnlyOne.class);
					 addressReturn = addressOnlyOne.getAddres1();
					} catch (Exception e) {

						addressReturn = output2;
					}
				}
			}

		}

		// System.out.println(addressReturn);
		return addressReturn;
	}
}
