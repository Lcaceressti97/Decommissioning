package hn.com.tigo.equipmentaccessoriesbilling.dto;

public class ProcessResult {
    private boolean success;
    private String message;

    public ProcessResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}