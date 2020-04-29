package com.nikolas.master_thesis.util;

public class DWBException extends Throwable {

    private int code;

    public DWBException(int code) {
        this(code, "Error while processing the request", null);
    }

    public DWBException(int code, String message) {
        this(code, message, null);
    }

    public DWBException(int code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
