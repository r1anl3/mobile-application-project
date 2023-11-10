package com.example.myapplication.API;

import com.example.myapplication.GlobalVar;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private String token = "";

    public Retrofit getClient() {
        return new Retrofit.Builder()
                .baseUrl(GlobalVar.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
}
