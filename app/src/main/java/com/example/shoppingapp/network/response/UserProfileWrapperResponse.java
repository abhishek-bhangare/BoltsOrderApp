package com.example.shoppingapp.network.response;

import com.google.gson.annotations.SerializedName;

public class UserProfileWrapperResponse {

    @SerializedName("status")
    private boolean status;

    @SerializedName("data")
    private UserProfile data;

    public boolean isStatus() {
        return status;
    }

    public UserProfile getData() {
        return data;
    }
}
