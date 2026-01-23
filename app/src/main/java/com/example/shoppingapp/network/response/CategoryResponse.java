package com.example.shoppingapp.network.response;

import com.example.shoppingapp.modelclass.CategoryModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryResponse {

    @SerializedName("Status")
    private boolean status;

    @SerializedName("Data")
    private List<CategoryModel> data;

    public boolean isStatus() {
        return status;
    }

    public List<CategoryModel> getData() {
        return data;
    }
}
