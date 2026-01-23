package com.example.shoppingapp.network.response;

import com.google.gson.annotations.SerializedName;

public class OtpGenerationResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}