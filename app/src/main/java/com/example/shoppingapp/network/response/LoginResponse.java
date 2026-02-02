
//new response
package com.example.shoppingapp.network.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("cust_id")
    private String custId;

    @SerializedName("unique_id")
    private String uniqueId;

    @SerializedName("Status")
    private boolean status;

    public String getCustId() {
        return custId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public boolean isStatus() {
        return status;
    }
}
