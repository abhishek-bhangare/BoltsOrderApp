package com.example.shoppingapp.network.response;

import com.google.gson.annotations.SerializedName;

public class SubCategoryResponse {

    @SerializedName("subcate_id")
    private String subcateId;

    @SerializedName("sub_catename")
    private String subCatename;

    public String getSubcateId() {
        return subcateId;
    }

    public String getSubCatename() {
        return subCatename;
    }
}
