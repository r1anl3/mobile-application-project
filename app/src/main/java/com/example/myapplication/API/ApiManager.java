package com.example.myapplication.API;

import android.util.Log;

import com.example.myapplication.GlobalVar;
import com.example.myapplication.Manager.LocalDataManager;
import com.example.myapplication.Model.Token;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class ApiManager {
    private static final ApiClient client = new ApiClient();
    private static final ApiService service = client.getClient().create(ApiService.class);

    public static Token getToken(String user, String pass) {
        Call<Token> call = service.getToken(GlobalVar.grantType, GlobalVar.clientId, user, pass);
        try {
            Response<Token> response = call.execute();
            if (response.isSuccessful()) {
                Token token = response.body();
                ApiClient.token = token.access_token;
                Log.d(GlobalVar.LOG_TAG, "token: " + ApiClient.token);
                return token;
            }
            else { Log.d(GlobalVar.LOG_TAG, "getToken: Not Successful"); }
        } catch (IOException e) {
//            e.printStackTrace();
            Log.d(GlobalVar.LOG_TAG, "exception: " + e.getMessage());
        }
        return null;
    }
}
