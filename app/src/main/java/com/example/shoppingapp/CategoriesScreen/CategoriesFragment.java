
package com.example.shoppingapp.CategoriesScreen;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.network.ApiClient;
import com.example.shoppingapp.network.ApiService;
import com.example.shoppingapp.network.request.MainCategoryRequest;
import com.example.shoppingapp.network.request.SubCategoryRequest;
import com.example.shoppingapp.network.response.MainCategoryResponse;
import com.example.shoppingapp.network.response.SubCategoryResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesFragment extends Fragment {

    private static final String TAG = "CategoriesFragment";

    // ================= UI =================
    private RecyclerView rvMainCategory;   // 🔥 NEW (Main Category)
    private RecyclerView rvCategory1;      // 🔥 EXISTING (Sub Category)
    private TextView tvNoData;

    // ================= API =================
    private ApiService apiService;

    // ================= DATA =================
    // 🔥 Default vehicle type → Two Wheeler (same as HomeFragment)
    private String comId = "1";
    private String mcateId;   // Main category ID
    private boolean isFromArgs = false;


    public CategoriesFragment() {
        // Required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {

        Log.d(TAG, "onCreateView: CategoriesFragment started");

        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        // ===============================
        // INIT VIEWS
        // ===============================
        rvMainCategory = view.findViewById(R.id.rvMainCategory); // may be null (safe)
        rvCategory1 = view.findViewById(R.id.rvCategory1);
        tvNoData = view.findViewById(R.id.tvNoData);

        // ===============================
        // SUB CATEGORY RECYCLER SETUP (EXISTING)
        // ===============================
        rvCategory1.setLayoutManager(
                new LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                )
        );
        rvCategory1.setHasFixedSize(true);
        rvCategory1.setNestedScrollingEnabled(false);

        // ===============================
        // MAIN CATEGORY RECYCLER SETUP (NEW - SAFE)
        // ===============================
        if (rvMainCategory != null) {
            rvMainCategory.setLayoutManager(
                    new LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                    )
            );
            rvMainCategory.setHasFixedSize(true);
            rvMainCategory.setNestedScrollingEnabled(false);

            Log.d(TAG, "Main Category RecyclerView initialized");
        } else {
            Log.w(TAG, "rvMainCategory not found in layout (safe)");
        }

        // ===============================
        // GET ARGUMENTS (EXISTING LOGIC)
        // ===============================
        if (getArguments() != null) {
            comId = getArguments().getString("com_id");
            mcateId = getArguments().getString("mcate_id");
            isFromArgs = mcateId != null;
        }


        Log.d(TAG, "Received com_id: " + comId);
        Log.d(TAG, "Received mcate_id: " + mcateId);

        // ===============================
        // API INIT
        // ===============================
        apiService = ApiClient.getClient().create(ApiService.class);
        Log.d(TAG, "ApiService initialized");

        // ===============================
        // 🔴 EXISTING VALIDATION (UNCHANGED)
        // ===============================
        if (comId == null) {
            Log.e(TAG, "com_id missing");
            showNoData("Invalid category data");
            return view;
        }

        // ===============================
        // 🔥 EXISTING FLOW (UNCHANGED)
        // If mcateId comes from Home → load directly
        // ===============================
        if (mcateId != null) {
            Log.d(TAG, "Loading subcategories from arguments");
            loadSubCategories(comId, mcateId);
        }

        // ===============================
        // 🔥 NEW: LOAD MAIN CATEGORIES SAFELY
        // (Does NOT break existing logic)
        // ===============================
        loadMainCategoriesSafely();

        return view;
    }

    // =====================================================
    // 🔥 MAIN CATEGORY API (NEW, SAFE ADDITION)
    // =====================================================
    private void loadMainCategoriesSafely() {

        Log.d(TAG, "Calling Main Category API");

        MainCategoryRequest request = new MainCategoryRequest(comId);

        apiService.getMainCategories(request)
                .enqueue(new Callback<List<MainCategoryResponse>>() {

                    @Override
                    public void onResponse(
                            Call<List<MainCategoryResponse>> call,
                            Response<List<MainCategoryResponse>> response) {

                        Log.d(TAG, "Main Category API response code: " + response.code());

                        if (!response.isSuccessful()
                                || response.body() == null
                                || response.body().isEmpty()) {

                            Log.w(TAG, "Main category list empty or failed");
                            return;
                        }

                        List<MainCategoryResponse> list = response.body();
                        Log.d(TAG, "Main category count: " + list.size());

                        if (rvMainCategory != null) {

                            MainCategoryAdapter adapter =
                                    new MainCategoryAdapter(list, model -> {

                                        Log.d(TAG,
                                                "Main category clicked → "
                                                        + model.getCategoryName()
                                                        + " | mcate_id="
                                                        + model.getMcateId());

                                        mcateId = model.getMcateId();
                                        loadSubCategories(comId, mcateId);
                                    });

                            rvMainCategory.setAdapter(adapter);
                        }

                        // 🔥 If fragment opened directly (no mcateId)
                        if (!isFromArgs && mcateId == null && !list.isEmpty()) {
                            mcateId = list.get(0).getMcateId();
                            Log.d(TAG,
                                    "Default main category selected → mcate_id=" + mcateId);
                            loadSubCategories(comId, mcateId);
                        }

                    }

                    @Override
                    public void onFailure(
                            Call<List<MainCategoryResponse>> call,
                            Throwable t) {

                        Log.e(TAG, "Main Category API failure", t);
                    }
                });
    }

    // =====================================================
    // 🔥 SUBCATEGORY API CALL (UNCHANGED)
    // =====================================================
    private void loadSubCategories(String comId, String mcateId) {

        Log.d(TAG, "Calling SubCategory API");
        Log.d(TAG, "Request → com_id=" + comId + ", mcate_id=" + mcateId);

        SubCategoryRequest request =
                new SubCategoryRequest(comId, mcateId);

        apiService.getSubCategories(request)
                .enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(
                            Call<ResponseBody> call,
                            Response<ResponseBody> response) {

                        if (!response.isSuccessful() || response.body() == null) {
                            Log.e(TAG, "SubCategory API failed or empty");
                            showNoData("Something went wrong");
                            return;
                        }

                        try {
                            String json = response.body().string().trim();
                            Log.d(TAG, "SubCategory API response: " + json);

                            if (json.startsWith("[")) {

                                List<SubCategoryResponse> list =
                                        new Gson().fromJson(
                                                json,
                                                new TypeToken<List<SubCategoryResponse>>() {}.getType()
                                        );

                                if (list == null || list.isEmpty()) {
                                    showNoData("No categories found");
                                } else {
                                    rvCategory1.setVisibility(View.VISIBLE);
                                    tvNoData.setVisibility(View.GONE);

                                    CategoryItemAdapter adapter =
                                            new CategoryItemAdapter(
                                                    list,
                                                    model -> {

                                                        Log.d(TAG,
                                                                "Subcategory clicked → "
                                                                        + model.getSubCatename()
                                                                        + " | ID="
                                                                        + model.getSubcateId());

                                                        Toast.makeText(
                                                                getContext(),
                                                                model.getSubCatename(),
                                                                Toast.LENGTH_SHORT
                                                        ).show();
                                                    });

                                    rvCategory1.setAdapter(adapter);
                                }

                            } else {
                                showNoData("No categories found");
                            }

                        } catch (Exception e) {
                            Log.e(TAG, "SubCategory parsing error", e);
                            showNoData("Something went wrong");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e(TAG, "SubCategory API failure", t);
                        showNoData("Network error");
                    }
                });
    }

    // =====================================================
    // 🔹 EMPTY / ERROR STATE HANDLER (UNCHANGED)
    // =====================================================
    private void showNoData(String message) {
        rvCategory1.setVisibility(View.GONE);
        tvNoData.setVisibility(View.VISIBLE);
        tvNoData.setText(message);
        Log.w(TAG, "showNoData: " + message);
    }
}
