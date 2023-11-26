package com.example.myapplication.Model;

import com.google.gson.annotations.SerializedName;

public class Token {
    @SerializedName("access_token")
    private String access_token;
    @SerializedName("expires_in")
    private long expires_in;
    @SerializedName("scope")
    private String scope;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
