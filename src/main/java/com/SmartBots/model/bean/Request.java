package com.SmartBots.model.bean;

public class Request {
    int Request_Id;
    int Company_Id;
    int Number_Of_Positions_Required;
    String Qualification_Requirements;
    String Duty_Station;
    String Department_Division;
    String Brief_Description;
    String Date_Unix;
    String Status;
    String level;
    String field;

    public Request(int request_Id, int company_Id, int number_Of_Positions_Required, String qualification_Requirements, String duty_Station, String department_Division, String brief_Description, String date_Unix, String status, String level, String field) {
        Request_Id = request_Id;
        Company_Id = company_Id;
        Number_Of_Positions_Required = number_Of_Positions_Required;
        Qualification_Requirements = qualification_Requirements;
        Duty_Station = duty_Station;
        Department_Division = department_Division;
        Brief_Description = brief_Description;
        Date_Unix = date_Unix;
        Status = status;
        this.level = level;
        this.field = field;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public int getRequest_Id() {
        return Request_Id;
    }

    public void setRequest_Id(int request_Id) {
        Request_Id = request_Id;
    }

    public int getCompany_Id() {
        return Company_Id;
    }

    public void setCompany_Id(int company_Id) {
        Company_Id = company_Id;
    }

    public int getNumber_Of_Positions_Required() {
        return Number_Of_Positions_Required;
    }

    public void setNumber_Of_Positions_Required(int number_Of_Positions_Required) {
        Number_Of_Positions_Required = number_Of_Positions_Required;
    }

    public String getQualification_Requirements() {
        return Qualification_Requirements;
    }

    public void setQualification_Requirements(String qualification_Requirements) {
        Qualification_Requirements = qualification_Requirements;
    }

    public String getDuty_Station() {
        return Duty_Station;
    }

    public void setDuty_Station(String duty_Station) {
        Duty_Station = duty_Station;
    }

    public String getDepartment_Division() {
        return Department_Division;
    }

    public void setDepartment_Division(String department_Division) {
        Department_Division = department_Division;
    }

    public String getBrief_Description() {
        return Brief_Description;
    }

    public void setBrief_Description(String brief_Description) {
        Brief_Description = brief_Description;
    }

    public String getDate_Unix() {
        return Date_Unix;
    }

    public void setDate_Unix(String date_Unix) {
        Date_Unix = date_Unix;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
