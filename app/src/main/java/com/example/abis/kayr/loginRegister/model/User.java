package com.example.abis.kayr.loginRegister.model;

/**
 * Created by abis.
 */

public class User {

    private String name;
    private String email;
    private String userID;
    private String password;
    private String created_at;
    private String newPassword;
    private String token;


    public void setName(String name) {
        this.name = name;
    }
    public void setBRID(String BRID){ this.userID=BRID;}

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }
    public String getBRID(){ return userID;}

    public String getEmail() {
        return email;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
