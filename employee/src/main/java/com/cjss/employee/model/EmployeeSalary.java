package com.cjss.employee.model;


public class EmployeeSalary {
    private String id;
    private double salary;
    private  String payable;

    public EmployeeSalary() {
    }

    public EmployeeSalary(String id, double salary, String payable) {
        this.id = id;
        this.salary = salary;
        this.payable = payable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

}
