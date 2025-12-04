package hn.com.tigo.inquiriesamsysnavega.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import hn.com.tigo.inquiriesamsysnavega.models.GeneralError;
import hn.com.tigo.inquiriesamsysnavega.models.GeneralResponse;

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

	public static void captureAndSetResponse(HttpServletRequest request, ResponseEntity<Object> responseEntity) {
		request.setAttribute("RESPONSE_ENTITY", responseEntity);
	}
}
