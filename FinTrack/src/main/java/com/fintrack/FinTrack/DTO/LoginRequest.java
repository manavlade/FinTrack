package com.fintrack.FinTrack.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email format")
    private String employeeEmail;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String employeePassword;

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public String getEmployeePassword() {
        return employeePassword;
    }

}
