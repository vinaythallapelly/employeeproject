package com.cjss.employee.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
public class EmployeeEntity {
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String employeeId;
    private String name;
    private String email;
    @OneToOne(mappedBy = "employeeDetails")
    EmployeeSalaryEntity employeeSalary;
    @OneToMany(mappedBy = "employeeDetails")
    List<EmployeeAddEntity> employeeAddressSet = new ArrayList<>();
    @OneToMany(mappedBy = "employeeDetails")
    List<EmployeeAttEntity> employeeAttendanceList = new ArrayList<>();

    public EmployeeEntity() {
    }

    public EmployeeEntity(String employeeId, String name, String email) {
        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmployeeSalaryEntity getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(EmployeeSalaryEntity employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public List<EmployeeAddEntity> getEmployeeAddressSet() {
        return employeeAddressSet;
    }

    public void setEmployeeAddressSet(List<EmployeeAddEntity> employeeAddressSet) {
        this.employeeAddressSet = employeeAddressSet;
    }

    public List<EmployeeAttEntity> getEmployeeAttendanceList() {
        return employeeAttendanceList;
    }

    public void setEmployeeAttendanceList(List<EmployeeAttEntity> employeeAttendanceList) {
        this.employeeAttendanceList = employeeAttendanceList;
    }

}
