package com.cjss.employee.model;


public class EmployeeAttendance {
    private String id;
    private String date;
    private boolean holiday;
    private String reasonForHoliday;

    public EmployeeAttendance() {
    }

    public EmployeeAttendance(String id, String date, boolean holiday, String reasonForHoliday) {
        this.id = id;
        this.date = date;
        this.holiday = holiday;
        this.reasonForHoliday = reasonForHoliday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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


}
