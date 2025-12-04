package hn.com.tigo.equipmentblacklist.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import hn.com.tigo.equipmentblacklist.models.GeneralError;
import hn.com.tigo.equipmentblacklist.models.GeneralResponse;
import hn.com.tigo.equipmentblacklist.models.GeneralResponseImei;

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

	public GeneralResponseImei setSuccessResponseICF(Object data, String phone) {
		GeneralResponseImei gr = new GeneralResponseImei();
		gr.setCode(0L);
		gr.setDescription("Operation Successful");
		gr.setPhone(phone);
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

}
