package com.fintrack.FinTrack.DTO;

import com.fintrack.FinTrack.models.enums.Role;

public class SignUpResponse {
    private Long id;
    private String employeeEmail;
    private Role employeeRole;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public Role getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(Role employeeRole) {
        this.employeeRole = employeeRole;
    }

}
