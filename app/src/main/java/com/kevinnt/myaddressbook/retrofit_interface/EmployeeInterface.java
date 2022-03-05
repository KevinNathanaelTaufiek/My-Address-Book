package com.kevinnt.myaddressbook.retrofit_interface;

import com.kevinnt.myaddressbook.models.GetEmployeeEndpoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EmployeeInterface {

    @GET("/stage2/people?nim=2301907901&nama=Kevin%20Nathanael%20Taufiek")
    Call<GetEmployeeEndpoint> getAllEmployees();

    @GET("/stage2/people/{id}/?nim=2301907901&nama=Kevin%20Nathanael%20Taufiek")
    Call<GetEmployeeEndpoint> getEmployeeDetail(@Path("id") int id);

}
