//package com.example.shoppingapp.network.response;
//
//import com.google.gson.annotations.SerializedName;
//
//public class LoginResponse {
//
//    @SerializedName("c_name")          // maps to: "10"
//    private String c_id;
//
//    @SerializedName("unique_id")       // maps to: "SAP-1111111111"
//    private String unique_id;
//
//    @SerializedName("custname")        // maps to: "Ashwini"
//    private String cust_name;
//
//    @SerializedName("cust_city")       // maps to: "Pune"
//    private String cust_city;
//
//    @SerializedName("cust_mob")        // maps to: "1111111111"
//    private String cust_mobile;
//
//    @SerializedName("Status")          // maps to: true
//    private boolean Status;
//
//    // Getters
//    public String getC_id() {
//        return c_id;
//    }
//
//    public String getUnique_id() {
//        return unique_id;
//    }
//
//    public String getCust_name() {
//        return cust_name;
//    }
//
//    public String getCust_city() {
//        return cust_city;
//    }
//
//    public String getCust_mobile() {
//        return cust_mobile;
//    }
//
//    public boolean isStatus() {
//        return Status;
//    }
//}

//new response
package com.example.shoppingapp.network.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("cust_id")
    private String custId;

    @SerializedName("unique_id")
    private String uniqueId;

    @SerializedName("Status")
    private boolean status;

    public String getCustId() {
        return custId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public boolean isStatus() {
        return status;
    }
}
