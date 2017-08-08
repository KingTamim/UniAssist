package com.goribmanush.uniassist;

/**
 * Created by Tamim on 7/21/2017.
 */

public class UserInformation {

    public String name;
    public String sid;
    public String department;
    public String semester;
    public Integer batch;
    public String cgpa = "Pending...";


    public UserInformation(){

    }

    public UserInformation(String name, String sid, String department, String semester, Integer batch) {
        this.name = name;
        this.sid = sid;
        this.department = department;
        this.semester = semester;
        this.batch = batch;

    }

    public String getName() {
        return name;
    }

    public String getSid() {
        return sid;
    }

    public String getDepartment() {
        return department;
    }

    public String getSemester() {
        return semester;
    }

    public Integer getBatch() {
        return batch;
    }

    public String getCgpa() {
        return cgpa;
    }
}
