
package com.example.shoppingapp.subcategory;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.network.ApiClient;
import com.example.shoppingapp.network.ApiService;
import com.example.shoppingapp.network.request.SubCategoryRequest;
import com.example.shoppingapp.network.response.SubCategoryResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoryActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView tvSelectedPart;
    private RecyclerView rvSubCategory;

    private ApiService apiService;
    private TextView tvNoData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sub_category);
        Window window = getWindow();
        // Status bar color
        window.setStatusBarColor(Color.parseColor("#696FC7"));

        // 🔹 Edge-to-Edge Insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    systemBars.bottom
            );
            return insets;
        });

        // 🔹 Init Views
        btnBack = findViewById(R.id.btnBack);
        tvSelectedPart = findViewById(R.id.tvSelectedPart);
        rvSubCategory = findViewById(R.id.rvSubCategory);
        tvNoData = findViewById(R.id.tvNoData);


        // 🔹 Back Button
        btnBack.setOnClickListener(v -> finish());

        // 🔹 RecyclerView Setup
        rvSubCategory.setLayoutManager(new GridLayoutManager(this, 2));

        // 🔹 Get Category ID from Intent
        int categoryId = getIntent().getIntExtra("category_id", 0);

        if (categoryId == 0) {
            Toast.makeText(this, "Invalid category", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 🔹 API Service
        apiService = ApiClient.getClient().create(ApiService.class);

        // 🔹 Call SubCategory API
        loadSubCategories(categoryId);
    }

    // ================= API CALL =================

    private void loadSubCategories(int categoryId) {

        apiService.getSubCategories(new SubCategoryRequest(categoryId))
                .enqueue(new Callback<SubCategoryResponse>() {
                    @Override
                    public void onResponse(Call<SubCategoryResponse> call,
                                           Response<SubCategoryResponse> response) {

//
                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().isStatus()
                                && response.body().getSubcategories() != null
                                && !response.body().getSubcategories().isEmpty()) {

                            // 🔹 Show RecyclerView
                            rvSubCategory.setVisibility(View.VISIBLE);
                            tvNoData.setVisibility(View.GONE);

                            // 🔹 Set Category Name from API
                            tvSelectedPart.setText(response.body().getCatname());

                            // 🔹 Bind RecyclerView
                            SubCategoryAdapter adapter =
                                    new SubCategoryAdapter(
                                            response.body().getSubcategories()
                                    );
                            rvSubCategory.setAdapter(adapter);

                        } else {
                            // 🔹 No Data Found
                            rvSubCategory.setVisibility(View.GONE);
                            tvNoData.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<SubCategoryResponse> call, Throwable t) {
                        rvSubCategory.setVisibility(View.GONE);
                        tvNoData.setVisibility(View.VISIBLE);
                        tvNoData.setText("Something went wrong. Please try again.");
                    }

                });
    }
}
