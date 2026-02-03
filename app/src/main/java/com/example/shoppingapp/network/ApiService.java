package com.example.shoppingapp.network;

import com.example.shoppingapp.network.request.ItemRequest;
import com.example.shoppingapp.network.request.LoginRequest;
import com.example.shoppingapp.network.request.MainCategoryRequest;
import com.example.shoppingapp.network.request.RegisterRequest;
import com.example.shoppingapp.network.request.SubCategoryRequest;
import com.example.shoppingapp.network.response.ItemResponse;
import com.example.shoppingapp.network.response.MainCategoryResponse;
import com.example.shoppingapp.network.response.MobileCheckResponse;
import com.example.shoppingapp.network.response.OtpResponse;
import com.example.shoppingapp.network.response.RegisterResponse;
import com.example.shoppingapp.network.response.SubCategoryResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

//     //✅ Check Mobile Number API
//    @GET("api_checkmobile.php")
//    Call<MobileCheckResponse> checkMobile(
//            @Query("mobile_no") String mobileNo
//    );

    // 🔹 REGISTER API
//    @POST("api_registration.php")
//    Call<RegisterResponse> registerUser(
//            @Body RegisterRequest request
//    );

    // ✅ LOGIN USER  ⭐⭐⭐
//    @POST("api_customerLogin.php")
//    Call<LoginResponse> loginUser(
//            @Body LoginRequest request
//    );
//    //Get Categories
//    @POST("api_getCategories.php")
//    Call<CategoryResponse> getCategories();

    @POST("api_maincategory.php")
    Call<List<MainCategoryResponse>> getMainCategories(
            @Body MainCategoryRequest request
    );

    @POST("api_subcategory.php")
    Call<ResponseBody> getSubCategories(
            @Body SubCategoryRequest request
    );


    @POST("api_subcateitemlist.php")
    Call<ItemResponse> getItemList(@Body ItemRequest request);

}
