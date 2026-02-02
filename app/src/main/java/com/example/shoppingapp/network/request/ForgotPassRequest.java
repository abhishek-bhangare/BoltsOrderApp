package com.example.shoppingapp.network.request;

import com.google.gson.annotations.SerializedName;

public class ForgotPassRequest {

    @SerializedName("c_mob")
    private String cMob;

    @SerializedName("unique_id")
    private String uniqueId;

    @SerializedName("pass")
    private String pass;

    @SerializedName("c_pass")
    private String cPass;

    public ForgotPassRequest(String cMob, String uniqueId, String pass, String cPass) {
        this.cMob = cMob;
        this.uniqueId = uniqueId;
        this.pass = pass;
        this.cPass = cPass;
    }

    // Optional getters (good practice)
    public String getcMob() {
        return cMob;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getPass() {
        return pass;
    }

    public String getcPass() {
        return cPass;
    }
}
