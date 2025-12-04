package hn.com.tigo.equipmentinsurance.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import hn.com.tigo.equipmentinsurance.models.GeneralError;
import hn.com.tigo.equipmentinsurance.models.GeneralResponse;

import javax.servlet.http.HttpServletRequest;

public class ResponseBuilder {
	private final Util util;

	public ResponseBuilder() {
		this.util = new Util();
	}

	public ResponseEntity<Object> buildSuccessResponse(HttpServletRequest request, Object data) {

		ResponseEntity<Object> responseEntity;

		if (data != null) {
			responseEntity = ResponseEntity.ok(util.setSuccessResponse(data));

		} else {
			responseEntity = ResponseEntity.ok(util.setSuccessWithoutData());
		}

		Util.captureAndSetResponse(request, responseEntity);
		return responseEntity;
	}

	public ResponseEntity<Object> buildErrorResponse(Exception ex, HttpServletRequest request) {
		ResponseEntity<Object> responseEntityBody = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		HttpStatus statusCode = responseEntityBody.getStatusCode();
		int statusCodeValue = statusCode.value();
		String statusDescription = statusCode.getReasonPhrase();

		GeneralError error = new GeneralError();
		error.setCode(Long.toString(statusCodeValue));
		error.setUserMessage(ex.getMessage());
		error.setMoreInfo("Contact to admin for more information");
		error.setInternalMessage("Check the log for more information");

		GeneralResponse errorResponse = new GeneralResponse();
		errorResponse.setCode(Long.valueOf(statusCodeValue));
		errorResponse.setDescription(statusDescription);
		errorResponse.getErrors().add(error);

		ResponseEntity<Object> responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(errorResponse);
		Util.captureAndSetResponse(request, responseEntity);
		return responseEntity;
	}

	public ResponseEntity<Object> buildErrorResponseCorrect(Exception ex, HttpServletRequest request,
			HttpStatus httpStatus) {
		GeneralError error = new GeneralError();
		error.setCode(String.valueOf(httpStatus.value()));
		error.setUserMessage(ex.getMessage());
		error.setMoreInfo("Contact to admin for more information");
		error.setInternalMessage("Check the log for more information");

		GeneralResponse errorResponse = new GeneralResponse();
		errorResponse.setCode((long) httpStatus.value());
		errorResponse.setDescription(httpStatus.getReasonPhrase());
		errorResponse.getErrors().add(error);

		ResponseEntity<Object> responseEntity = ResponseEntity.status(httpStatus).body(errorResponse);
		Util.captureAndSetResponse(request, responseEntity);
		return responseEntity;
	}

}
