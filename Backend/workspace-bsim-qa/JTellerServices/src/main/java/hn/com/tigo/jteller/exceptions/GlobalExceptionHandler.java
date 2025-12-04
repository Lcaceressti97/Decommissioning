package hn.com.tigo.jteller.exceptions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import hn.com.tigo.jteller.utils.Util;
import hn.com.tigo.jteller.models.GeneralResponse;
import hn.com.tigo.jteller.models.GeneralError;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
    private final Util util;

    public GlobalExceptionHandler() {
        this.util = new Util();
    }
    
    /**
     * Este método controla la excepción de la anotación @Valid
     * 
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        GeneralResponse response = new GeneralResponse();
        List<GeneralError> errorsList = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            GeneralError err = new GeneralError();
            err.setUserMessage(error.getDefaultMessage());
            err.setCode("-1");
            errorsList.add(err);
        });
        response.setCode(-1L);
        response.setErrors(errorsList);
        response.setDescription("Argument not valid");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }



    /**
     * Este método controla la excepción de un body malo
     * 
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        GeneralResponse response = new GeneralResponse();
          response.setCode(-1L);
        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handle(Exception ex, HttpServletRequest request, HttpServletResponse response) {

    	
        if(ex instanceof BadRequestException) {
            return new ResponseEntity<>(this.util.setError(ex,"contact to admin for more information"),HttpStatus.BAD_REQUEST);
        }
    	 

        return new ResponseEntity<>(this.util.setError(ex, ex.getLocalizedMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    

}
