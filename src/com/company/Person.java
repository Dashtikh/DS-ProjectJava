package com.company;

import java.util.List;

public class Person {
    private int id;
    private String fName;
    private String lName;
    private boolean sex;
    private int bDate;
    private int dDate;
    private int spouseId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public int getbDate() {
        return bDate;
    }

    public void setbDate(int bDate) {
        this.bDate = bDate;
    }

    public int getdDate() {
        return dDate;
    }

    public void setdDate(int dDate) {
        this.dDate = dDate;
    }

    public int getSpouseId() {
        return spouseId;
    }

    public void setSpouseId(int spouseId) {
        this.spouseId = spouseId;
    }
}
