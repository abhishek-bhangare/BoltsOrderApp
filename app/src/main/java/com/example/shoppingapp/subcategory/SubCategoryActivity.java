//
//package com.example.shoppingapp.subcategory;
//
//import android.graphics.Color;
//import android.os.Bundle;
//import android.view.View;
//import android.view.Window;
//import android.widget.ImageButton;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.shoppingapp.R;
//import com.example.shoppingapp.network.ApiClient;
//import com.example.shoppingapp.network.ApiService;
//import com.example.shoppingapp.network.request.SubCategoryRequest;
//import com.example.shoppingapp.network.response.SubCategoryResponse;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class SubCategoryActivity extends AppCompatActivity {
//
//    private ImageButton btnBack;
//    private TextView tvSelectedPart;
//    private RecyclerView rvSubCategory;
//
//    private ApiService apiService;
//    private TextView tvNoData;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_sub_category);
//        Window window = getWindow();
//        // Status bar color
//        window.setStatusBarColor(Color.parseColor("#696FC7"));
//
//        // 🔹 Edge-to-Edge Insets
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(
//                    systemBars.left,
//                    systemBars.top,
//                    systemBars.right,
//                    systemBars.bottom
//            );
//            return insets;
//        });
//
//        // 🔹 Init Views
//        btnBack = findViewById(R.id.btnBack);
//        tvSelectedPart = findViewById(R.id.tvSelectedPart);
//        rvSubCategory = findViewById(R.id.rvSubCategory);
//        tvNoData = findViewById(R.id.tvNoData);
//
//
//        // 🔹 Back Button
//        btnBack.setOnClickListener(v -> finish());
//
//        // 🔹 RecyclerView Setup
//        rvSubCategory.setLayoutManager(new GridLayoutManager(this, 2));
//
//        // 🔹 Get Category ID from Intent
//        int categoryId = getIntent().getIntExtra("category_id", 0);
//
//        if (categoryId == 0) {
//            Toast.makeText(this, "Invalid category", Toast.LENGTH_SHORT).show();
//            finish();
//            return;
//        }
//
//        // 🔹 API Service
//        apiService = ApiClient.getClient().create(ApiService.class);
//
//        // 🔹 Call SubCategory API
//        loadSubCategories(categoryId);
//    }
//
//    // ================= API CALL =================
//
//    private void loadSubCategories(int categoryId) {
//
//        apiService.getSubCategories(new SubCategoryRequest(categoryId))
//                .enqueue(new Callback<SubCategoryResponse>() {
//                    @Override
//                    public void onResponse(Call<SubCategoryResponse> call,
//                                           Response<SubCategoryResponse> response) {
//
////
//                        if (response.isSuccessful()
//                                && response.body() != null
//                                && response.body().isStatus()
//                                && response.body().getSubcategories() != null
//                                && !response.body().getSubcategories().isEmpty()) {
//
//                            // 🔹 Show RecyclerView
//                            rvSubCategory.setVisibility(View.VISIBLE);
//                            tvNoData.setVisibility(View.GONE);
//
//                            // 🔹 Set Category Name from API
//                            tvSelectedPart.setText(response.body().getCatname());
//
//                            // 🔹 Bind RecyclerView
//                            SubCategoryAdapter adapter =
//                                    new SubCategoryAdapter(
//                                            response.body().getSubcategories()
//                                    );
//                            rvSubCategory.setAdapter(adapter);
//
//                        } else {
//                            // 🔹 No Data Found
//                            rvSubCategory.setVisibility(View.GONE);
//                            tvNoData.setVisibility(View.VISIBLE);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<SubCategoryResponse> call, Throwable t) {
//                        rvSubCategory.setVisibility(View.GONE);
//                        tvNoData.setVisibility(View.VISIBLE);
//                        tvNoData.setText("Something went wrong. Please try again.");
//                    }
//
//                });
//    }
//}

package com.example.shoppingapp.subcategory;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.network.ApiClient;
import com.example.shoppingapp.network.ApiService;
import com.example.shoppingapp.network.request.SubCategoryRequest;
import com.example.shoppingapp.network.response.SubCategoryResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoryActivity extends AppCompatActivity {

    private static final String TAG = "SubCategoryActivity"; // 🔍 Logcat TAG

    // UI
    private ImageButton btnBack;
    private TextView tvSelectedPart;
    private RecyclerView rvSubCategory;
    private TextView tvNoData;

    // API
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        // 🔹 Status bar color
        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#696FC7"));

        // 🔹 INIT VIEWS
        btnBack = findViewById(R.id.btnBack);
        tvSelectedPart = findViewById(R.id.tvSelectedPart);
        rvSubCategory = findViewById(R.id.rvSubCategory);
        tvNoData = findViewById(R.id.tvNoData);

        // 🔹 BACK BUTTON
        btnBack.setOnClickListener(v -> finish());

        // 🔹 RECYCLER VIEW SETUP
        rvSubCategory.setLayoutManager(new GridLayoutManager(this, 2));
        rvSubCategory.setHasFixedSize(true);

        // =====================================================
        // 🔥 RECEIVE DATA FROM PREVIOUS SCREEN (IMPORTANT)
        // =====================================================
        Intent intent = getIntent();
        if (intent == null) {
            Log.e(TAG, "Intent is NULL");
            finish();
            return;
        }

        String mcateId = intent.getStringExtra("mcate_id");
        String comId = intent.getStringExtra("com_id");
        String categoryName = intent.getStringExtra("category_name");


        Log.d(TAG, "Received mcate_id: " + mcateId);
        Log.d(TAG, "Received com_id: " + comId);
        Log.d(TAG, "Received category_name: " + categoryName);

        // 🔴 VALIDATION
        if (mcateId == null || comId == null) {
            Log.e(TAG, "Intent data missing. Closing screen.");
            Toast.makeText(this, "Invalid category data", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 🔹 SET TITLE
        if (categoryName != null) {
            tvSelectedPart.setText(categoryName);
        }

        // 🔹 API SERVICE INIT
        apiService = ApiClient.getClient().create(ApiService.class);

        // 🔥 CALL SUBCATEGORY API
        loadSubCategories(comId, mcateId);
    }

    // =====================================================
    // 🔥 SUBCATEGORY API CALL
    // =====================================================
    private void loadSubCategories(String comId, String mcateId) {

        Log.d(TAG, "Calling SubCategory API");
        Log.d(TAG, "Request → com_id=" + comId + ", mcate_id=" + mcateId);

        // 🔹 REQUEST BODY
        SubCategoryRequest request =
                new SubCategoryRequest(comId, mcateId);

        apiService.getSubCategories(request)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (!response.isSuccessful() || response.body() == null) {
                            showNoData("Something went wrong");
                            return;
                        }

                        try {
                            String json = response.body().string().trim();

                            if (json.startsWith("[")) {
                                List<SubCategoryResponse> list =
                                        new Gson().fromJson(
                                                json,
                                                new TypeToken<List<SubCategoryResponse>>(){}.getType()
                                        );

                                if (list == null || list.isEmpty()) {
                                    showNoData("No subcategories found");
                                } else {
                                    rvSubCategory.setVisibility(View.VISIBLE);
                                    tvNoData.setVisibility(View.GONE);
                                    rvSubCategory.setAdapter(new SubCategoryAdapter(list));
                                }
                            } else {
                                // Object → No data case
                                showNoData("No subcategories found");
                            }

                        } catch (Exception e) {
                            showNoData("Something went wrong");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        showNoData("Something went wrong");
                    }
                });

    }

    // =====================================================
    // 🔹 EMPTY / ERROR STATE HANDLER
    // =====================================================
    private void showNoData(String message) {
        rvSubCategory.setVisibility(View.GONE);
        tvNoData.setVisibility(View.VISIBLE);
        tvNoData.setText(message);
    }
}
