package com.example.myapplication;

public class GlobalVar {
    public static String baseUrl = "https://uiot.ixxc.dev/";
    public static String signInUrl = baseUrl + "auth/realms/master/login-actions/authenticate";
    public static String resetPwsUrl = baseUrl + "auth/realms/master/login-actions/reset-credentials";
    public static String LampUrl = "http://122.248.192.235/";
    public static String LOG_TAG = "API LOG";
    public static String clientId = "openremote";
    public static String grantType = "password";
    public static int DELAY_TIME = 100;
    public static String ASSET_ID = "5zI6XqkQVSfdgOrZ1MyWEf";
    public static String lamp_server_key = "tPmAT5Ab3j7F9";
    public static String sensor = ASSET_ID;
}
