package com.nikolas.master_thesis.util;

import org.apache.http.HttpStatus;

public class StoreException extends RuntimeException {

    private int httpStatus;
    private String message;

    public StoreException(String message, int httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
