package hn.com.tigo.equipmentaccessoriesbilling.exceptions;

import lombok.Getter;

@Getter
public class VoucherException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Props

    

    
    public VoucherException(String message) {
    
		super(message);
    }
	
}
