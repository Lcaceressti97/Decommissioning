package hn.com.tigo.comodatos.exceptions;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import hn.com.tigo.comodatos.utils.ResponseBuilder;



public class ExceptionHandler {

	private final ResponseBuilder responseBuilder;

	public ExceptionHandler(ResponseBuilder responseBuilder) {
		this.responseBuilder = responseBuilder;
	}

	public ResponseEntity<Object> handleExceptions(ExceptionSupplier supplier, HttpServletRequest request) {
		try {
			return responseBuilder.buildSuccessResponse(request, supplier.get());
		} catch (BadRequestException e) {
			return responseBuilder.buildErrorResponseNew(e, request, HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			return responseBuilder.buildErrorResponseNew(e, request, HttpStatus.NOT_FOUND);
		} catch (DataAccessException e) {
			return responseBuilder.buildErrorResponseNew(e, request, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return responseBuilder.buildErrorResponseNew(e, request, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<Object> handleControllerExceptions(ControllerExceptionHandler exceptionHandler,
	        HttpServletRequest request) {
	    try {
	        ResponseEntity<?> response = exceptionHandler.handle();
	        return ResponseEntity.status(response.getStatusCode())
	                .headers(response.getHeaders())
	                .body(response.getBody());
	    } catch (BadRequestException e) {
	        return responseBuilder.buildErrorResponseNew(e, request, HttpStatus.BAD_REQUEST);
	    } catch (NotFoundException e) {
	        return responseBuilder.buildErrorResponseNew(e, request, HttpStatus.NOT_FOUND);
	    } catch (DataAccessException e) {
	        return responseBuilder.buildErrorResponseNew(e, request, HttpStatus.INTERNAL_SERVER_ERROR);
	    } catch (Exception e) {
	        return responseBuilder.buildErrorResponseNew(e, request, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	@FunctionalInterface
	public interface ExceptionSupplier<T> {
		T get() throws Exception;
	}

	@FunctionalInterface
	public interface ControllerExceptionHandler {
		ResponseEntity<?> handle() throws Exception;
	}
}
