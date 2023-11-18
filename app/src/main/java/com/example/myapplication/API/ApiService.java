package com.example.myapplication.API;

import com.example.myapplication.Model.Token;
import com.example.myapplication.Model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("auth/realms/master/protocol/openid-connect/token")
    Call<Token> getToken(@Field("grant_type") String type,
                         @Field("client_id") String client,
                         @Field("username") String user,
                         @Field("password") String pws);
    @GET("api/master/user/user")
    Call<User> getUser();
}
