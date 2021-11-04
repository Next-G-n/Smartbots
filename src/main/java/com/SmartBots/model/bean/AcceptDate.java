package com.SmartBots.model.bean;

public class AcceptDate {
    int accept;
    int user_id;
    int request_id;
    String Start_date;
    String End_date;
    String status;

    public AcceptDate(int accept, int user_id, int request_id, String start_date, String end_date, String status) {
        this.accept = accept;
        this.user_id = user_id;
        this.request_id = request_id;
        Start_date = start_date;
        End_date = end_date;
        this.status = status;
    }

    public int getAccept() {
        return accept;
    }

    public void setAccept(int accept) {
        this.accept = accept;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public String getStart_date() {
        return Start_date;
    }

    public void setStart_date(String start_date) {
        Start_date = start_date;
    }

    public String getEnd_date() {
        return End_date;
    }

    public void setEnd_date(String end_date) {
        End_date = end_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
