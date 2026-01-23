package com.example.shoppingapp.network;

import com.example.shoppingapp.network.request.LoginRequest;
import com.example.shoppingapp.network.response.EditProfileResponse;
import com.example.shoppingapp.network.response.LoginResponse;
import com.example.shoppingapp.network.response.MobileCheckResponse;
import com.example.shoppingapp.network.response.RegisterResponse;
import com.example.shoppingapp.network.response.UserProfile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApiService {

    @GET("api_mobile_nocheck.php")
    Call<MobileCheckResponse>checkMobile(@Query("c_mob") String cMob);

    @POST("api_custlogin.php")
    Call<LoginResponse> login(@Body LoginRequest request);

    @FormUrlEncoded
    @POST("api_custregistration.php")
    Call<RegisterResponse> registerUser(
            @Field("cust_nm") String companyName,
            @Field("city") String username,
            @Field("cust_mob") String mobile,
            @Field("pwd") String password,
            @Field("date") String createdOn
    );

    @GET("api_userprofile.php")
    Call<List<UserProfile>> getUserProfile(@Query("id") String uniqueId);


    @FormUrlEncoded
    @POST("api_eduserprofile.php")
    Call<EditProfileResponse> updateUserProfile(
            @Field("unique_id") String uniqueId,
            @Field("u_id") String userId,
            @Field("cust_name") String name,
            @Field("cust_mob") String mobile,
            @Field("email") String email,
            @Field("add1") String address1,
            @Field("add2") String address2,
            @Field("city") String city,
            @Field("area") String area,
            @Field("landmark") String landmark,
            @Field("state") String state,
            @Field("pincode") String pincode,
            @Field("edited_on") String editedOn   // ✅ ADD THIS
    );

}
