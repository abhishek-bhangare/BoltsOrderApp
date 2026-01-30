package com.example.shoppingapp.network.request;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class MobileCheckRequest implements Serializable {

    @SerializedName("c_mob")
    private String cMob;

    public MobileCheckRequest(String cMob) {
        this.cMob = cMob;
    }

    public String getCMob() {
        return cMob;
    }
}
