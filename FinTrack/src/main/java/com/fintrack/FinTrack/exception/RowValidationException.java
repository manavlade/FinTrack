package com.fintrack.FinTrack.exception;

import java.util.List;

public class RowValidationException extends RuntimeException {
    private final List<String> errors;
    
   public RowValidationException(List<String> errors) {
        super("Row validation errors identified: " + errors.size() + " error(s)"); // ← FIXED
        this.errors = errors;
    }
  public List<String> getErrors() {
        return errors;
    }
}