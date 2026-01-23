package com.example.shoppingapp.network.response;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class UserProfile implements Parcelable {

    @SerializedName("u_id")
    private String uId;

    @SerializedName("cust_name")
    private String custName;

    @SerializedName("cust_mob")
    private String custMobile;

    @SerializedName("email")
    private String email;

    @SerializedName("c_address")
    private String address;

    @SerializedName("c_address1")
    private String address1;

    @SerializedName("c_city")
    private String city;

    @SerializedName("area")
    private String area;

    @SerializedName("landmark")
    private String landmark;

    @SerializedName("state")
    private String state;

    @SerializedName("pincode")
    private String pincode;

    @SerializedName("unique_id")
    private String uniqueId;

    // Parcelable implementation
    protected UserProfile(Parcel in) {
        uId = in.readString();
        custName = in.readString();
        custMobile = in.readString();
        email = in.readString();
        address = in.readString();
        address1 = in.readString();
        city = in.readString();
        area = in.readString();
        landmark = in.readString();
        state = in.readString();
        pincode = in.readString();
        uniqueId = in.readString();
    }

    public static final Creator<UserProfile> CREATOR = new Creator<UserProfile>() {
        @Override
        public UserProfile createFromParcel(Parcel in) {
            return new UserProfile(in);
        }

        @Override
        public UserProfile[] newArray(int size) {
            return new UserProfile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uId);
        dest.writeString(custName);
        dest.writeString(custMobile);
        dest.writeString(email);
        dest.writeString(address);
        dest.writeString(address1);
        dest.writeString(city);
        dest.writeString(area);
        dest.writeString(landmark);
        dest.writeString(state);
        dest.writeString(pincode);
        dest.writeString(uniqueId);
    }

    // Getters
    public String getuId() { return uId; }
    public String getCustName() { return custName; }
    public String getCustMobile() { return custMobile; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public String getAddress1() { return address1; }
    public String getCity() { return city; }
    public String getArea() { return area; }
    public String getLandmark() { return landmark; }
    public String getState() { return state; }
    public String getPincode() { return pincode; }
    public String getUniqueId() { return uniqueId; }
}
