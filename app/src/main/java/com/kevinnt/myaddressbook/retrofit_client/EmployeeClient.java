package com.kevinnt.myaddressbook.retrofit_client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmployeeClient {

    public static final String BASE_URL = "https://u73olh7vwg.execute-api.ap-northeast-2.amazonaws.com/";

    private static Retrofit instance;

    public static Retrofit getInstance(){
        if (instance == null)
            instance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        return instance;
    }

}
