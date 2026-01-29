package com.example.shoppingapp.network.request;

public class MainCategoryRequest {

    private String com_id;

    public MainCategoryRequest(String com_id) {
        this.com_id = com_id;
    }

    public String getCom_id() {
        return com_id;
    }

    public void setCom_id(String com_id) {
        this.com_id = com_id;
    }
}