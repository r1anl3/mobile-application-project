package com.example.myapplication;

public class GlobalVar {
    public static String baseUrl = "https://uiot.ixxc.dev/";
    public static String signUpUrl = baseUrl + "auth/realms/master/login-actions/";
    public static String tokenUrl = baseUrl + "auth/realms/master/protocol/openid-connect/token";
    public static String LOG_TAG = "API LOG";
    public static int DELAY_TIME = 100;
}