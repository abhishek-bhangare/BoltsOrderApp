package com.example.shoppingapp.network.response;

import com.google.gson.annotations.SerializedName;

public class MainCategoryResponse {

    @SerializedName("mcate_id")
    private String mcateId;

    @SerializedName("m_categoryname")
    private String categoryName;

    public String getMcateId() {
        return mcateId;
    }

    public String getCategoryName() {
        return categoryName;
    }
}