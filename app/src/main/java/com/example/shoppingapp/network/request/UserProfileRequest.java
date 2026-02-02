package com.example.shoppingapp.network.request;

import com.google.gson.annotations.SerializedName;

public class UserProfileRequest {

    @SerializedName("cust_id")
    private String custId;

    @SerializedName("unique_id")
    private String uniqueId;

    public UserProfileRequest(String custId, String uniqueId) {
        this.custId = custId;
        this.uniqueId = uniqueId;
    }

    public String getCustId() {
        return custId;
    }

    public String getUniqueId() {
        return uniqueId;
    }
}
