package com.fintrack.FinTrack.DTO;

import com.fintrack.FinTrack.models.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SignUpRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email format")
    private String employeeEmail;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String employeePassword;


    @NotNull(message="Role is required")
    private Role employeeRole;

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeePassword() {
        return employeePassword;
    }

    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }

    public Role getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(Role employeeRole) {
        this.employeeRole = employeeRole;
    }
}
