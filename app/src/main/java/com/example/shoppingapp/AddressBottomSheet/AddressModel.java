//package com.example.shoppingapp.AddressBottomSheet;
//
//public class AddressModel {
//    private String title;
//    private String fullAddress;
//
//    public AddressModel(String title, String fullAddress) {
//        this.title = title;
//        this.fullAddress = fullAddress;
//    }
//
//    public String getTitle() { return title; }
//    public String getFullAddress() { return fullAddress; }
//}

package com.example.shoppingapp.AddressBottomSheet;

public class AddressModel {

    private String title;
    private String fullAddress;
    private String state; // ✅ REQUIRED FOR GST

    public AddressModel(String title, String fullAddress, String state) {
        this.title = title;
        this.fullAddress = fullAddress;
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public String getState() {
        return state;
    }
}
