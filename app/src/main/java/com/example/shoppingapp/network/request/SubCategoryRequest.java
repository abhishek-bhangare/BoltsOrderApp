package com.example.shoppingapp.network.request;

import com.google.gson.annotations.SerializedName;

public class SubCategoryRequest {

    @SerializedName("com_id")
    private String comId;

    @SerializedName("mcate_id")
    private String mcateId;

    public SubCategoryRequest(String comId, String mcateId) {
        this.comId = comId;
        this.mcateId = mcateId;
    }

    public String getComId() {
        return comId;
    }

    public String getMcateId() {
        return mcateId;
    }
}
