package hn.com.tigo.bsimmodule.utils;

import hn.com.tigo.bsimmodule.models.GeneralError;
import hn.com.tigo.bsimmodule.models.GeneralResponse;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

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

    public static <T> void captureAndSetResponse(HttpServletRequest request, ResponseEntity<T> responseEntity) {
        request.setAttribute("RESPONSE_ENTITY", responseEntity);
    }
}
