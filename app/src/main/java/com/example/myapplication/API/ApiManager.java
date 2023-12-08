package com.example.myapplication.API;

import android.util.Log;

import com.example.myapplication.GlobalVar;
import com.example.myapplication.Model.Asset;
import com.example.myapplication.Model.Device;
import com.example.myapplication.Model.Lamp;
import com.example.myapplication.Model.Token;
import com.example.myapplication.Model.User;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ApiManager {
    private static final ApiClient client = new ApiClient();
    private static final ApiService service = client.getClient().create(ApiService.class);
    private static final ApiService lampService = client.getLampClient().create(ApiService.class);

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
                assert token != null;
                ApiClient.token = token.getAccess_token(); // Update Api client token
                Log.d(GlobalVar.LOG_TAG, "getToken: " + ApiClient.token); // Log Api client token
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
                Log.d(GlobalVar.LOG_TAG, "getUser: " + User.getMe().getUsername()); // Log username
            }
            else { Log.d(GlobalVar.LOG_TAG, "getUser: Not Successful"); } // If fail
        } catch (IOException e) {
//            e.printStackTrace();
            Log.d(GlobalVar.LOG_TAG, "exception: " + e.getMessage()); // Print exception
        }
    }

    public static void getAsset(String assetId) {
        // Get user info
        Call<Asset> call = service.getAsset(assetId); // Call api get asset by assetId
        try {
            Response<Asset> response = call.execute(); // Get response from server
            if (response.isSuccessful()) { // If success
                Asset.setMe(response.body());
                Log.d(GlobalVar.LOG_TAG, "getAsset: " + Asset.getMe()
                        .getAttributes()
                        .getTemperature()
                        .getValue()); // Log asset
            }
            else { Log.d(GlobalVar.LOG_TAG, "getAsset: Not Successful"); } // If fail
        } catch (IOException e) {
//            e.printStackTrace();
            Log.d(GlobalVar.LOG_TAG, "exception: " + e.getMessage()); // Print exception
        }
    }

    public static void queryDevices(JsonObject body) {
        // Get all devices
        Call<List<Device>> call = service.queryDevices(body); // Call api post all devices
        try {
            Response<List<Device>> response = call.execute(); // Get response
            if (response.isSuccessful() && response.code() == 200) { //
                List<Device> deviceList = response.body(); // Assign response to list devices
                Device.setDevicesList(deviceList); // Set device list
                Log.d(GlobalVar.LOG_TAG, "queryDevices: " + Device.getDevicesList().size()); // Log api
            } else {
                Device.setDevicesList(null);
                Log.d(GlobalVar.LOG_TAG, "queryDevices: Not successful");
            }
        } catch (IOException e) { e.printStackTrace();}
    }

    public static void postLamp(String location,
                                float humid,
                                float temp,
                                float windSpeed,
                                float rainFall) {
        Call<Lamp> call = lampService.postLamp(GlobalVar.lamp_server_key,
                GlobalVar.sensor,
                location,
                humid,
                temp,
                windSpeed,
                rainFall); // Call api get token using 4 fields
        try {
            Response<Lamp> response = call.execute(); // Get response from server
            if (response.isSuccessful()) { // If success
                Lamp lampResponse = response.body(); // Assign response to token
                Log.d(GlobalVar.LOG_TAG, "postLamp: " + response.code());
            }
            else { Log.d(GlobalVar.LOG_TAG, "postLamp: Not Successful"); } // If fail
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(GlobalVar.LOG_TAG, "exception: " + e.getMessage()); // Print exception
        }
    }

}