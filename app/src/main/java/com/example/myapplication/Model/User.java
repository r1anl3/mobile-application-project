package com.example.myapplication.Model;

import com.google.gson.annotations.SerializedName;

public class User {
    public String realm;
    public String realmId;

    public String id;
    public String firstName;
    public String lastName;

    public String email;
    public boolean enabled;
    public long createdOn;
    public boolean serviceAccount;
    public String username;
    private static User me;

    public static User getMe() {
        return me;
    }
    public static void setMe(User u) {
        me = u;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getRealmId() {
        return realmId;
    }

    public void setRealmId(String realmId) {
        this.realmId = realmId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public boolean isServiceAccount() {
        return serviceAccount;
    }

    public void setServiceAccount(boolean serviceAccount) {
        this.serviceAccount = serviceAccount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
