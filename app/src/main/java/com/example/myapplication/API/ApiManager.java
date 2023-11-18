package com.example.myapplication.API;

import android.util.Log;

import com.example.myapplication.GlobalVar;
import com.example.myapplication.Manager.LocalDataManager;
import com.example.myapplication.Model.Token;
import com.example.myapplication.Model.User;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class ApiManager {
    private static final ApiClient client = new ApiClient();
    private static final ApiService service = client.getClient().create(ApiService.class);

    public static Token getToken(String user, String pass) {
        // Get token
        Call<Token> call = service.getToken(GlobalVar.grantType,
                GlobalVar.clientId,
                user,
                pass); // Call api get token using 4 fields
        try {
            Response<Token> response = call.execute(); // Get response from server
            if (response.isSuccessful()) { // If success
                Token token = response.body(); // Assign response to token
                ApiClient.token = token.access_token; // Update Api client token
                Log.d(GlobalVar.LOG_TAG, "token: " + ApiClient.token); // Log Api client token
                return token; // Return token
            }
            else { Log.d(GlobalVar.LOG_TAG, "getToken: Not Successful"); } // If fail
        } catch (IOException e) {
//            e.printStackTrace();
            Log.d(GlobalVar.LOG_TAG, "exception: " + e.getMessage()); // Print exception
        }
        return null; // Return null if exception
    }

    public static void getUser() {
        // Get user info
        Call<User> call = service.getUser(); // Call api get user
        try {
            Response<User> response = call.execute(); // Get response from server
            if (response.isSuccessful()) { // If success
                User.setMe(response.body()); // Set me with repose
                Log.d(GlobalVar.LOG_TAG, "Username: " + User.getMe().getUsername()); // Log username
            }
            else { Log.d(GlobalVar.LOG_TAG, "getUser: Not Successful"); } // If fail
        } catch (IOException e) {
//            e.printStackTrace();
            Log.d(GlobalVar.LOG_TAG, "exception: " + e.getMessage()); // Print exception
        }
    }
}
