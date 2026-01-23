//package com.example.shoppingapp.network.response;
//
//import com.google.gson.annotations.SerializedName;
//
//public class ForgotPassResponse {
//
//    @SerializedName("status")
//    private boolean status;
//
//    @SerializedName("message")
//    private String message;
//
//    public boolean isStatus() {
//        return status;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//}


package com.example.shoppingapp.network.response;

import com.google.gson.annotations.SerializedName;

public class ForgotPassResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    public boolean isStatus() {
        return "success".equalsIgnoreCase(status);
    }

    public String getMessage() {
        return message;
    }
}