package com.example.shoppingapp.network.request;

public class RegisterRequest {

    private String name;
    private String mobile_no;
    private String birth_day;
    private String gst_no;
    private String pass;

    public RegisterRequest(String name, String mobile_no,
                           String birth_day, String gst_no, String pass) {
        this.name = name;
        this.mobile_no = mobile_no;
        this.birth_day = birth_day;
        this.gst_no = gst_no;
        this.pass = pass;
    }
}
