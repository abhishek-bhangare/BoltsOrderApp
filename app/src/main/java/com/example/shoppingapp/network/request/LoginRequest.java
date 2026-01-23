package com.example.shoppingapp.network.request;

public class LoginRequest {
    private String username;
    private String pass;

    public LoginRequest(String username, String pass) {
        this.username = username;
        this.pass = pass;
    }
}