package com.kevinnt.myaddressbook.models;

public class EmployeeTableModel {

    private int employeeId;
    private String name;
    private String city;
    private String call;
    private String email;
    private String profileUrl;

    public EmployeeTableModel(int employeeId, String name, String city, String call, String email, String profileUrl) {
        this.employeeId = employeeId;
        this.name = name;
        this.city = city;
        this.call = call;
        this.email = email;
        this.profileUrl = profileUrl;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
