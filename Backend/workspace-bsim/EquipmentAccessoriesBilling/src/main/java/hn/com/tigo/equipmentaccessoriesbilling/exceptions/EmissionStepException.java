package hn.com.tigo.equipmentaccessoriesbilling.exceptions;

public class EmissionStepException extends RuntimeException {
    private final String service;
    private final String errorCode;
    private final String userMessage;

    public EmissionStepException(String service, String errorCode, String userMessage, Throwable cause) {
        super("[" + service + "] " + errorCode + ": " + userMessage, cause);
        this.service = service;
        this.errorCode = errorCode;
        this.userMessage = userMessage;
    }

    public String getService() { return service; }
    public String getErrorCode() { return errorCode; }
    public String getUserMessage() { return userMessage; }
}
