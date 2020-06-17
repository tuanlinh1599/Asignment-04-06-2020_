package com.quannar178.managestudent;

class StudentModel {
    public int getMssv() {
        return mssv;
    }

    public void setMssv(int mssv) {
        this.mssv = mssv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getLoca() {
        return loca;
    }

    public void setLoca(String loca) {
        this.loca = loca;
    }

    private int mssv;
    private String name;
    private String birthDay;
    private String gmail;
    private String loca;

    public StudentModel(int mssv, String name, String birthDay, String gmail, String loca) {
        this.mssv = mssv;
        this.name = name;
        this.birthDay = birthDay;
        this.gmail = gmail;
        this.loca = loca;
    }
}
