package hn.com.tigo.jteller.exceptions;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Props

    

    
    public BadRequestException(String message) {
    
		super(message);
    }


    
}
