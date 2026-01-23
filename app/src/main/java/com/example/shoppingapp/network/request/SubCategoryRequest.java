package com.example.shoppingapp.network.request;


public class SubCategoryRequest {

    private int catid;

    public SubCategoryRequest(int catid) {
        this.catid = catid;
    }

    public int getCatid() {
        return catid;
    }
}