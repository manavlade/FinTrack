package com.fintrack.FinTrack.exception;

public class ExcelValidationException extends RuntimeException {

    public ExcelValidationException(String message){
        super(message);
    }

    public ExcelValidationException(String message, Throwable cause){
        super(message, cause);
    }
}