package com.SmartBots.model.bean;

public class Companyinfo {
    int companyId;
    int user_id;
    String companyName;
    String  city;
    String email;
    String sector;
    String vat;
    String registration_Number;
    String Physical_address;
    String tel;

    public Companyinfo(int companyId, int user_id, String companyName, String city, String email, String sector, String vat, String registration_Number, String physical_address, String tel) {
        this.companyId = companyId;
        this.user_id = user_id;
        this.companyName = companyName;
        this.city = city;
        this.email = email;
        this.sector = sector;
        this.vat = vat;
        this.registration_Number = registration_Number;
        Physical_address = physical_address;
        this.tel = tel;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getRegistration_Number() {
        return registration_Number;
    }

    public void setRegistration_Number(String registration_Number) {
        this.registration_Number = registration_Number;
    }

    public String getPhysical_address() {
        return Physical_address;
    }

    public void setPhysical_address(String physical_address) {
        Physical_address = physical_address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
