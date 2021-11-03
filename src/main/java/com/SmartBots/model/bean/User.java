package com.SmartBots.model.bean;

public class User {
    int user_id;
    String first_Name;
    String last_name;
    String password;
    String email;
    int omang;
    String phone_number;
    String gender;
    String location;
    String userType;


    public User(String first_Name, String last_name, String password, String email, int omang, String phone_number, String gender, String location, String userType) {
        this.first_Name = first_Name;
        this.last_name = last_name;
        this.password = password;
        this.email = email;
        this.omang = omang;
        this.phone_number = phone_number;
        this.gender = gender;
        this.location = location;
        this.userType = userType;
    }

    public User(int user_id, String first_Name, String last_name, String password, String email, int omang, String phone_number, String gender, String location, String userType) {
        this.user_id = user_id;
        this.first_Name = first_Name;
        this.last_name = last_name;
        this.password = password;
        this.email = email;
        this.omang = omang;
        this.phone_number = phone_number;
        this.gender = gender;
        this.location = location;
        this.userType = userType;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFirst_Name() {
        return first_Name;
    }

    public void setFirst_Name(String first_Name) {
        this.first_Name = first_Name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getOmang() {
        return omang;
    }

    public void setOmang(int omang) {
        this.omang = omang;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
