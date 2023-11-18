package com.example.myapplication.API;

import com.example.myapplication.GlobalVar;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static String token = "";
    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            //Log
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);

            //Bear token
            builder.addInterceptor(chain -> {
                Request newRequest = chain.request()
                        .newBuilder()
                        .addHeader("Authorization", "Bearer " + token)
                        .build();

                return chain.proceed(newRequest);
            });

            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Retrofit getClient() {
        OkHttpClient client = getUnsafeOkHttpClient();
        return new Retrofit.Builder()
                .baseUrl(GlobalVar.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

    }
}
