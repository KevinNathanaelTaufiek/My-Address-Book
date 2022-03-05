package com.kevinnt.myaddressbook.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetEmployeeEndpoint {

    @SerializedName("nim")
    private String nim;

    @SerializedName("nama")
    private String nama;

    @SerializedName("employees")
    private List<Employee> employees;

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
