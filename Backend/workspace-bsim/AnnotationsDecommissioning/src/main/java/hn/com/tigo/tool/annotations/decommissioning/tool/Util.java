package hn.com.tigo.tool.annotations.decommissioning.tool;

import hn.com.tigo.tool.annotations.decommissioning.models.GeneralError;
import hn.com.tigo.tool.annotations.decommissioning.models.GeneralResponse;

public class Util {

	public GeneralResponse setSuccessResponse(Object data) {
		GeneralResponse gr = new GeneralResponse();
		gr.setCode(1L);
		gr.setDescription("Success");
		if (data != null) {
			gr.setData(data);
		}

		return gr;
	}

	public GeneralResponse setSuccessWithoutData() {
		GeneralResponse gr = new GeneralResponse();
		gr.setCode(1L);
		gr.setDescription("Success");
		return gr;
	}

	public GeneralResponse setSuccessEmail() {
		GeneralResponse gr = new GeneralResponse();
		gr.setCode(1L);
		gr.setDescription("Mail sent successfully");
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
}
