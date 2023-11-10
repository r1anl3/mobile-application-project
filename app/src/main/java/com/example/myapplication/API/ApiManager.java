package com.example.myapplication.API;

import android.util.Log;

import com.example.myapplication.GlobalVar;
import com.example.myapplication.Model.Token;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiManager {
    private ApiClient client = new ApiClient();
    private ApiService service = client.getClient().create(ApiService.class);


    public void getToken(String user, String pass) {
        // Todo: Get token
        Call<Token> call = service.getToken(GlobalVar.grantType, GlobalVar.clientId, user, pass);
//        try {
//            Response<Token>
//        }
    }
}
