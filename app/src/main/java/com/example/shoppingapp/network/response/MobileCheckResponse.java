//package com.example.shoppingapp.network.response;
//
//public class MobileCheckResponse {
//
//    private boolean status;
//    private String message;
//    private Data data;
//
//    public boolean isStatus() {
//        return status;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public Data getData() {
//        return data;
//    }
//
//    // 🔹 Inner class for "data" object
//    public static class Data {
//
//        private String id;
//        private String mobile_no;
//
//        public String getId() {
//            return id;
//        }
//
//        public String getMobile_no() {
//            return mobile_no;
//        }
//    }
//}

package com.example.shoppingapp.network.response;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class MobileCheckResponse implements Serializable {

    @SerializedName("cust_mobno")
    private String custMobNo;

    @SerializedName("unique_id")
    private String uniqueId;

    @SerializedName("Status")
    private boolean status;

    // ✅ Getters
    public String getCustMobNo() {
        return custMobNo;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public boolean isStatus() {
        return status;
    }
}
