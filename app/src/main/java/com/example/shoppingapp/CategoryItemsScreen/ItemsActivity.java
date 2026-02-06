//package com.example.shoppingapp.CategoryItemsScreen;
//
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.view.Window;
//import android.widget.ImageButton;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.content.ContextCompat;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsControllerCompat;
//
//import com.example.shoppingapp.R;
//
//public class ItemsActivity extends AppCompatActivity {
//
//    private ImageButton btnBack;
//    private TextView tvTitle;
//    private TextView tvSelectedPart;
//
//    private String scateId;
//    private String subCategoryName;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_items);
//
//        // 🔹 Status bar color
//        Window window = getWindow();
//        window.setStatusBarColor(ContextCompat.getColor(this, R.color.topBar));
//
//        // 🔹 INIT VIEWS
//        btnBack = findViewById(R.id.btnBack);
//        tvTitle = findViewById(R.id.tvTitle);
//        tvSelectedPart = findViewById(R.id.tvSelectedPart);
//
//        // 🔹 Fixed top bar title
//        tvTitle.setText("Items");
//
//        // 🔹 Back button
//        btnBack.setOnClickListener(v -> onBackPressed());
//
//        // 🔹 White status bar icons
//        WindowInsetsControllerCompat controller =
//                ViewCompat.getWindowInsetsController(window.getDecorView());
//        if (controller != null) {
//            controller.setAppearanceLightStatusBars(false);
//        }
//
//        // 🔹 Receive intent data
//        Intent intent = getIntent();
//        if (intent == null) {
//            Toast.makeText(this, "Invalid data", Toast.LENGTH_SHORT).show();
//            finish();
//            return;
//        }
//
//        scateId = intent.getStringExtra("scate_id");
//        subCategoryName = intent.getStringExtra("subcategory_name");
//
//        if (scateId == null) {
//            Toast.makeText(this, "Subcategory not found", Toast.LENGTH_SHORT).show();
//            finish();
//            return;
//        }
//
//        // 🔹 Update selected part name
//        if (subCategoryName != null) {
//            tvSelectedPart.setText(subCategoryName);
//        }
//
//        // Next: loadItems(scateId);
//    }
//}

package com.example.shoppingapp.CategoryItemsScreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.network.ApiClient;
import com.example.shoppingapp.network.ApiService;
import com.example.shoppingapp.network.request.ItemRequest;
import com.example.shoppingapp.network.response.ItemModel;
import com.example.shoppingapp.network.response.ItemResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemsActivity extends AppCompatActivity {

    private static final String TAG = "ItemsActivity";

    // UI
    private ImageButton btnBack;
    private TextView tvTitle, tvSelectedPart, tvNoData;
    private RecyclerView rvItems;

    // Adapter & Data
    private ItemsAdapter itemsAdapter;
    private List<ItemModel> itemList = new ArrayList<>();

    // Intent data
    private String comId;
    private String mainCateId;
    private String subCateId;
    private String subCategoryName;

    // API
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        // =====================================================
        // 🔹 STATUS BAR SETUP
        // =====================================================
        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.topBar));

        WindowInsetsControllerCompat controller =
                ViewCompat.getWindowInsetsController(window.getDecorView());
        if (controller != null) {
            controller.setAppearanceLightStatusBars(false);
        }

        // =====================================================
        // 🔹 INIT VIEWS
        // =====================================================
        btnBack = findViewById(R.id.btnBack);
        tvTitle = findViewById(R.id.tvTitle);
        tvSelectedPart = findViewById(R.id.tvSelectedPart);
        tvNoData = findViewById(R.id.tvNoData);
        rvItems = findViewById(R.id.rvItems);

        tvTitle.setText("Items");
        btnBack.setOnClickListener(v -> onBackPressed());

        // =====================================================
        // 🔹 RECYCLER VIEW SETUP
        // =====================================================
        rvItems.setLayoutManager(new GridLayoutManager(this, 2));
        rvItems.setHasFixedSize(true);

        itemsAdapter = new ItemsAdapter(this, itemList);
        rvItems.setAdapter(itemsAdapter);

        // =====================================================
        // 🔹 API INIT
        // =====================================================
        apiService = ApiClient.getClient().create(ApiService.class);

        // =====================================================
        // 🔹 RECEIVE INTENT DATA
        // =====================================================
        Intent intent = getIntent();
        if (intent == null) {
            showNoData("Invalid data");
            return;
        }

        comId = intent.getStringExtra("com_id");
        mainCateId = intent.getStringExtra("main_cateid");
        subCateId = intent.getStringExtra("sub_cateid");
        subCategoryName = intent.getStringExtra("subcategory_name");

        // 🔍 LOGCAT
        Log.d(TAG, "com_id=" + comId + ", main=" + mainCateId + ", sub=" + subCateId);

        // 🔴 VALIDATION
        if (comId == null || mainCateId == null || subCateId == null) {
            showNoData("Item data missing");
            return;
        }

        if (subCategoryName != null) {
            tvSelectedPart.setText(subCategoryName);
        }

        // =====================================================
        // 🔥 CALL ITEMS API
        // =====================================================
        loadItems();
    }

    // =====================================================
    // 🔥 ITEMS API CALL
    // =====================================================
    private void loadItems() {

        ItemRequest request = new ItemRequest(
                comId,
                mainCateId,
                subCateId
        );

        Log.d(TAG, "Calling Items API");

        apiService.getItemList(request).enqueue(new Callback<ItemResponse>() {

            @Override
            public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {

                // ❌ API / NETWORK FAILURE
                if (!response.isSuccessful() || response.body() == null) {
                    Log.e(TAG, "API failed: " + response.code());
                    showNoData("Failed to load items");
                    return;
                }

                ItemResponse itemResponse = response.body();

                // ❌ EMPTY DATA FROM API
                if (!itemResponse.isStatus() || itemResponse.getData() == null) {
                    Log.e(TAG, "No item data from API");
                    showNoData("No items available");
                    return;
                }

                // =====================================================
                // ✅ SUCCESS – UPDATE UI
                // =====================================================
                ItemModel item = itemResponse.getData();

                Log.d(TAG, "Item: " + item.getPartName());

                itemList.clear();
                itemList.add(item);

                rvItems.setVisibility(View.VISIBLE);
                tvNoData.setVisibility(View.GONE);

                itemsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ItemResponse> call, Throwable t) {
                Log.e(TAG, "API error", t);
                showNoData("Something went wrong");
            }
        });
    }

    // =====================================================
    // 🔹 COMMON EMPTY / ERROR HANDLER
    // =====================================================
    private void showNoData(String message) {
        itemList.clear();
        itemsAdapter.notifyDataSetChanged();

        rvItems.setVisibility(View.GONE);
        tvNoData.setVisibility(View.VISIBLE);
        tvNoData.setText(message);
    }
}
