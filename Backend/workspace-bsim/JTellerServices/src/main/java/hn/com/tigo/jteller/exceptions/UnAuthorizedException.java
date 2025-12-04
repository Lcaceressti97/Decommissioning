package hn.com.tigo.jteller.exceptions;

import lombok.Getter;

@Getter
public class UnAuthorizedException extends RuntimeException{

    private String message;
    private String internalMessage;
    private String moreInfo;


    public UnAuthorizedException() {
        super();
    }

    public UnAuthorizedException(String message) {
        super(message);
    }
    public UnAuthorizedException(String message, String internalMessage, String moreInfo) {
        this.message = message;
        this.moreInfo = moreInfo;
        this.internalMessage = internalMessage;
    }
    
}
