package com.example.shoppingapp.network.response;

import com.google.gson.annotations.SerializedName;

public class ItemResponse {

    @SerializedName("status")
    private boolean status;

    @SerializedName("data")
    private ItemModel data;

    public boolean isStatus() {
        return status;
    }

    public ItemModel getData() {
        return data;
    }
}
