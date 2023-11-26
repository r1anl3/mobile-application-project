package com.example.myapplication.API;

import com.example.myapplication.Model.Asset;
import com.example.myapplication.Model.Device;
import com.example.myapplication.Model.Token;
import com.example.myapplication.Model.User;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @FormUrlEncoded
    @POST("auth/realms/master/protocol/openid-connect/token")
    Call<Token> getToken(@Field("grant_type") String type,
                         @Field("client_id") String client,
                         @Field("username") String user,
                         @Field("password") String pws);
    @GET("api/master/user/user")
    Call<User> getUser();

    @GET("api/master/asset/{assetID}")
    Call<Asset> getAsset(@Path("assetID") String assetID);

    @Headers("Content-Type: application/json")
    @POST("api/master/asset/query")
    Call<List<Device>> queryDevices(@Body JsonObject body);

}
