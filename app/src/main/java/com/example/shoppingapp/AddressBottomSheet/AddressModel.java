package com.example.shoppingapp.AddressBottomSheet;

public class AddressModel {
    private String title;
    private String fullAddress;

    public AddressModel(String title, String fullAddress) {
        this.title = title;
        this.fullAddress = fullAddress;
    }

    public String getTitle() { return title; }
    public String getFullAddress() { return fullAddress; }
}
