package com.example.shoppingapp.network.response;

import com.example.shoppingapp.modelclass.SubCategoryModel;

import java.util.List;

public class SubCategoryResponse {
    private boolean Status;
    private String catid;
    private String catname;
    private List<SubCategoryModel> subcategories;

    public boolean isStatus() { return Status; }
    public String getCatname() { return catname; }
    public List<SubCategoryModel> getSubcategories() { return subcategories; }
}

