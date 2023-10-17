package com.example.myapplication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public interface ApiService {
    String linkApi = "https://uiot.ixxc.dev/auth/realms/master/protocol/openid-connect/token";
    Gson GSON = new GsonBuilder()
//            .setDateFormat("dd-MM-yyyyy HH:mm:ss")
            .create();
    ApiService API_SERVICE = new Retrofit.Builder()
            .baseUrl(linkApi)
            .addConverterFactory(GsonConverterFactory.create(GSON))
            .build()
            .create(ApiService.class);

//    @POST("auth/realms/master/protocol/openid-connect/token")
//    Call<Access>
}
