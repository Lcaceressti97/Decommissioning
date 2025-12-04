package hn.com.tigo.equipmentinsurance.utils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionHandler {

	private final ResponseBuilder responseBuilder;

	public ExceptionHandler(ResponseBuilder responseBuilder) {
		this.responseBuilder = responseBuilder;
	}

	public ResponseEntity<Object> handleExceptions(ExceptionSupplier supplier, HttpServletRequest request) {
		try {
			return responseBuilder.buildSuccessResponse(request, supplier.get());
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
