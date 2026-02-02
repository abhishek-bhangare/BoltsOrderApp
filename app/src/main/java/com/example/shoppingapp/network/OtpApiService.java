package com.example.shoppingapp.network;

import com.example.shoppingapp.network.request.ForgotPassRequest;
import com.example.shoppingapp.network.response.ForgotPassResponse;
import com.example.shoppingapp.network.response.OtpGenerationResponse;
import com.example.shoppingapp.network.response.OtpResponse;
import com.example.shoppingapp.network.response.OtpVerifyResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface OtpApiService {
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
//    @POST("api_forgetpass.php")
//    Call<ForgotPassResponse> forgotPassword(
//            @Field("c_mob") String mobile,
//            @Field("unique_id") String companyId,
//            @Field("pass") String password,
//            @Field("c_pass") String confPassword
//    );
        // ✅ FORGOT PASSWORD (JSON BODY)
        @POST("api_forgetpass.php")
        Call<ForgotPassResponse> forgotPassword(
        @Body ForgotPassRequest request
);

}
