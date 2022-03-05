package com.kevinnt.myaddressbook.models.employee_attribute;

import com.google.gson.annotations.SerializedName;

public class Picture {
    @SerializedName("medium")
    private String medium;

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "medium='" + medium + '\'' +
                '}';
    }
}
