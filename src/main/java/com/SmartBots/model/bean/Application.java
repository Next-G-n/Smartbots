package com.SmartBots.model.bean;

public class Application {

    int application_id;
    int user_id;
    String field;
    String TypeOfIntern;
    String Major;
    String level;
    String programme;
    String dateOfCompletion;
    String Disability;
    String school;
    String status;
    String qualification;
    String CV;

    public Application(int application_id, int user_id, String field, String typeOfIntern, String major, String level, String programme, String dateOfCompletion, String disability, String school, String status, String qualification, String CV) {
        this.application_id = application_id;
        this.user_id = user_id;
        this.field = field;
        TypeOfIntern = typeOfIntern;
        Major = major;
        this.level = level;
        this.programme = programme;
        this.dateOfCompletion = dateOfCompletion;
        Disability = disability;
        this.school = school;
        this.status = status;
        this.qualification = qualification;
        this.CV = CV;
    }

    public int getApplication_id() {
        return application_id;
    }

    public void setApplication_id(int application_id) {
        this.application_id = application_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getTypeOfIntern() {
        return TypeOfIntern;
    }

    public void setTypeOfIntern(String typeOfIntern) {
        TypeOfIntern = typeOfIntern;
    }

    public String getMajor() {
        return Major;
    }

    public void setMajor(String major) {
        Major = major;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public String getDateOfCompletion() {
        return dateOfCompletion;
    }

    public void setDateOfCompletion(String dateOfCompletion) {
        this.dateOfCompletion = dateOfCompletion;
    }

    public String getDisability() {
        return Disability;
    }

    public void setDisability(String disability) {
        Disability = disability;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getCV() {
        return CV;
    }

    public void setCV(String CV) {
        this.CV = CV;
    }
}
