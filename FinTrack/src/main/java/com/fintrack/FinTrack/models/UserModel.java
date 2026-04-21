package com.fintrack.FinTrack.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="employee")
public class UserModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String employeeEmail;
    private String employeePassword;
    private String employeeRoele;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getEmail() {
        return employeeEmail;
    }

    public void setEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getPassword() {
        return employeePassword;
    }

    public void setPassword(String employeePassword){
        this.employeePassword = employeePassword;
    }

    public String getEmployeeRoele() {
        return employeeRoele;
    }

    public void setEmployeeRoele(String employeeRoele) {
        this.employeeRoele = employeeRoele;
    }

    

}
