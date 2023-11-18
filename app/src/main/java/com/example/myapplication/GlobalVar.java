package com.example.myapplication;

public class GlobalVar {
    public static String baseUrl = "https://uiot.ixxc.dev/";
    public static String signUpUrl = baseUrl + "auth/realms/master/login-actions/registration";
    public static String signInUrl = baseUrl + "auth/realms/master/login-actions/authenticate";
    public static String tokenUrl = baseUrl + "auth/realms/master/protocol/openid-connect/token";
    public static String resetPwsUrl = baseUrl + "auth/realms/master/login-actions/reset-credentials";
    public static String changePwsUrl = baseUrl + "auth/realms/master/account/password";
    public static String LOG_TAG = "API LOG";
    public static String clientId = "openremote";
    public static String grantType = "password";
    public static int DELAY_TIME = 100;
}
