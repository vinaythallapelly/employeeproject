package com.cjss.employee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class EmployeeAddEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String employeeId;
    private String line1;
    private String line2;
    private String city;
    private String phoneNumber;
    private String country;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    EmployeeEntity employeeDetails;

    public EmployeeAddEntity() {
    }

    public EmployeeAddEntity(String employeeId, String line1, String line2, String city, String phoneNumber, String country) {
        this.employeeId = employeeId;
        this.line1 = line1;
        this.line2 = line2;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.country = country;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public EmployeeEntity getEmployeeDetails() {
        return employeeDetails;
    }

    public void setEmployeeDetails(EmployeeEntity employeeDetails1) {
        this.employeeDetails = employeeDetails1;
    }

}
