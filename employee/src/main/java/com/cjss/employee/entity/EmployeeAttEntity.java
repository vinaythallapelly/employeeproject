package com.cjss.employee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class EmployeeAttEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String employeeId;
    private String date;
    private boolean holiday;
    private String reasonForHoliday;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    EmployeeEntity employeeDetails;

    public EmployeeAttEntity() {
    }

    public EmployeeAttEntity(String employeeId, String date, boolean holiday, String reasonForHoliday) {
        this.employeeId = employeeId;
        this.date = date;
        this.holiday = holiday;
        this.reasonForHoliday = reasonForHoliday;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isHoliday() {
        return holiday;
    }

    public void setHoliday(boolean holiday) {
        this.holiday = holiday;
    }

    public String getReasonForHoliday() {
        return reasonForHoliday;
    }

    public void setReasonForHoliday(String reasonForHoliday) {
        this.reasonForHoliday = reasonForHoliday;
    }

    public EmployeeEntity getEmployeeDetails() {
        return employeeDetails;
    }

    public void setEmployeeDetails(EmployeeEntity employeeDetails2) {
        this.employeeDetails = employeeDetails2;
    }


}
