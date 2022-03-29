package com.cjss.employee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class EmployeeSalaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String employeeId;
    private double salary;
    private  String payable;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private EmployeeEntity employeeDetails;

    public EmployeeSalaryEntity() {
    }

    public EmployeeSalaryEntity(String employeeId, double salary, String payable, EmployeeEntity employeeDetails) {
        this.employeeId = employeeId;
        this.salary = salary;
        this.payable = payable;
        this.employeeDetails = employeeDetails;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getPayable() {
        return payable;
    }

    public void setPayable(String payable) {
        this.payable = payable;
    }

    public EmployeeEntity getEmployeeDetails() {
        return employeeDetails;
    }

    public void setEmployeeDetails(EmployeeEntity employeeDetails) {
        this.employeeDetails = employeeDetails;
    }

}
