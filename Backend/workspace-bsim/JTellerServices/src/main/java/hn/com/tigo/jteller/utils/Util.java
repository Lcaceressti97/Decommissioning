package hn.com.tigo.jteller.utils;

import hn.com.tigo.jteller.models.GeneralError;
import hn.com.tigo.jteller.models.GeneralResponse;

public class Util {

	public GeneralResponse setSuccessResponse(Object data) {
		GeneralResponse gr = new GeneralResponse();
		gr.setCode(0L);
		gr.setDescription("Operation Successful");


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
		
		
		gr.setCode(-1L);
		gr.setDescription("Error");
		gr.getErrors().add(error);

		return gr;
	}
	
	public static String formatDate(Long date) {
		
		
		if(date==null || date ==0) {
			return "";
		}else {
	        // Convertir a String
	        String stringValue = String.valueOf(date);
	        
	        // Extraer los valores
	        String year = stringValue.substring(0, 4);
	        String month = stringValue.substring(4, 6);
	        String day = stringValue.substring(6, 8);
	        String hour = stringValue.substring(8, 10);
	        String minute = stringValue.substring(10, 12);
	        String second = stringValue.substring(12, 14);
	        
	        // Construir la fecha formateada
	        String formattedDate = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
			
	        return formattedDate;
		}
		

        
	}

}
