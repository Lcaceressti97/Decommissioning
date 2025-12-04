package hn.com.tigo.selfconsumption.exceptions;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import hn.com.tigo.selfconsumption.utils.ResponseBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotFoundException;

public class ExceptionHandler {

	private final ResponseBuilder responseBuilder;

	public ExceptionHandler(ResponseBuilder responseBuilder) {
		this.responseBuilder = responseBuilder;
	}

	public ResponseEntity<Object> handleExceptions(ExceptionSupplier exceptionService, HttpServletRequest request) {
		try {
			return responseBuilder.buildSuccessResponse(request, exceptionService.get());
		} catch (BadRequestException e) {
			return responseBuilder.buildErrorResponseCorrect(e, request, HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			return responseBuilder.buildErrorResponseCorrect(e, request, HttpStatus.NOT_FOUND);
		} catch (DataAccessException e) {
			return responseBuilder.buildErrorResponseCorrect(e, request, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return responseBuilder.buildErrorResponseCorrect(e, request, HttpStatus.BAD_REQUEST);
		}
	}

	@FunctionalInterface
	public interface ExceptionSupplier<T> {
		T get() throws Exception;
	}

}
