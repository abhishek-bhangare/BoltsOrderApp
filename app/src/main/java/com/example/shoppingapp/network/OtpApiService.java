package com.example.shoppingapp.network;

import com.example.shoppingapp.network.response.ForgotPassResponse;
import com.example.shoppingapp.network.response.OtpGenerationResponse;
import com.example.shoppingapp.network.response.OtpResponse;
import com.example.shoppingapp.network.response.OtpVerifyResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface OtpApiService {
    // 🔹 OTP Generation API
//    @FormUrlEncoded
//    @POST("api_otpgeneration.php")
//    Call<OtpResponse> generateOtp(
//            @Field("mobile_no") String mobileNo
//    );
//      //otp verification api
//    @FormUrlEncoded
//    @POST("api_otpverification.php")
//    Call<OtpVerifyResponse> verifyOtp(
//            @Field("mobile_no") String mobileNo,
//            @Field("otp_no") String otpNo,
//            @Field("datetimes") String datetime
//    );

    @FormUrlEncoded
    @POST("api_otpgeneration.php")
    Call<OtpGenerationResponse> sendOtp(
            @Field("mobile_no") String mobileNo
    );

    // ✅ API call to validate OTP
    @FormUrlEncoded
    @POST("api_otpverification.php")
    Call<OtpGenerationResponse> validateOtp(
            @Field("mobile_no") String mobileNo,
            @Field("otp_no") String otp,
            @Field("datetimes") String datetime
    );

//    @FormUrlEncoded
//    @POST("api_forgotpass.php")
//    Call<ForgotPassResponse> forgotPassword(
//            @Field("mobile_no") String mobileNo,
//            @Field("cust_id") String custId,
//            @Field("password") String password
//    );

    @FormUrlEncoded
    @POST("api_forgetpass.php")
    Call<ForgotPassResponse> forgotPassword(
            @Field("c_mob") String mobile,
            @Field("unique_id") String companyId,
            @Field("pass") String password,
            @Field("c_pass") String confPassword
    );

}
